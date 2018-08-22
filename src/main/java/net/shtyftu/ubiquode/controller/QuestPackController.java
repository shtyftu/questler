package net.shtyftu.ubiquode.controller;

import static net.shtyftu.ubiquode.controller.AController.PACK_CONTROLLER_PATH;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;
import net.shtyftu.ubiquode.dao.plain.AccountDao;
import net.shtyftu.ubiquode.dao.plain.QuestProtoDao;
import net.shtyftu.ubiquode.model.QuestPack;
import net.shtyftu.ubiquode.model.persist.simple.Account;
import net.shtyftu.ubiquode.model.persist.simple.ModelWithId;
import net.shtyftu.ubiquode.model.persist.simple.QuestProto;
import net.shtyftu.ubiquode.model.projection.Quest;
import net.shtyftu.ubiquode.model.projection.User;
import net.shtyftu.ubiquode.model.view.QuestPackLightView;
import net.shtyftu.ubiquode.model.view.QuestPackView;
import net.shtyftu.ubiquode.model.view.QuestProtoView;
import net.shtyftu.ubiquode.processor.QuestPackRepository;
import net.shtyftu.ubiquode.processor.QuestRepository;
import net.shtyftu.ubiquode.processor.UserRepository;
import net.shtyftu.ubiquode.service.QuestPackService;
import net.shtyftu.ubiquode.service.QuestProtoService;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

/**
 * @author shtyftu
 */
@Controller
@RequestMapping(PACK_CONTROLLER_PATH)
public class QuestPackController extends AController {

    private final QuestProtoDao questProtoDao;
    private final UserRepository userProjector;
    private final QuestPackRepository questPackRepository;
    private final QuestRepository questProjector;
    private final QuestProtoService questProtoService;
    private final QuestPackService questPackService;
    private final AccountDao accountDao;

    @Autowired
    public QuestPackController(QuestProtoDao questProtoDao, UserRepository userProjector,
            QuestPackRepository questPackRepository, QuestRepository questProjector,
            QuestProtoService questProtoService, QuestPackService questPackService, AccountDao accountDao) {
        this.questProtoDao = questProtoDao;
        this.userProjector = userProjector;
        this.questPackRepository = questPackRepository;
        this.questProjector = questProjector;
        this.questProtoService = questProtoService;
        this.questPackService = questPackService;
        this.accountDao = accountDao;
    }

    @RequestMapping(value = LIST_PATH, method = RequestMethod.GET)
    public Map<String, Object> getList() {
        return getDefaultViewModel();
    }

    @Override
    protected Map<String, Object> getDefaultViewModel() {
        final String userId = getUserId();
        final User user = userProjector.getById(userId);
        final List<String> questPackIds = user.getQuestPackIds();
        final List<QuestPackLightView> view = questPackIds.stream()
                .map(questPackRepository::getById)
                .map((questPack) -> new QuestPackLightView(
                        questPack,
                        questPack.getProtoIdsByQuestId().values().stream()
                                .map(questProtoDao::getById)
                                .collect(Collectors.toMap(QuestProto::getName, ModelWithId::getId))
                ))
                .collect(Collectors.toList());
        return ImmutableMap.of("list", view);
    }

    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public ModelAndView create(@RequestParam(name = "name") String name) {
        final String userId = getUserId();
        final String packId = questPackService.create(name, userId);
        return getMessageView(ImmutableList.of("pack [" + packId + "] created"));
    }

    @RequestMapping(value = EDIT_PATH, method = RequestMethod.GET)
    public Map<String, Object> edit(@RequestParam(name = "packId") String packId) {
        final QuestPack pack = questPackRepository.getById(packId);
        final Set<String> questIds = pack.getProtoIdsByQuestId().keySet();
        final Map<String, String> questNamesById = questIds.stream()
                .map(questProjector::getById)
                .collect(Collectors.toMap(
                        Quest::getId,
                        q -> q.getProto().getName()));
        final Map<String, String> userNamesById = pack.getUserScores().keySet().stream()
                .collect(Collectors.toMap(id -> id, id -> id));
        final QuestPackView packView = new QuestPackView(pack, questNamesById, userNamesById);

        final Map<String, String> protosView = questProtoDao.getAll().stream()
                .collect(Collectors.toMap(QuestProto::getId, QuestProto::getName));

        final String userId = getUserId();
        final Map<Object, Object> invitesView = accountDao.getAll().stream()
                .filter(a -> !userId.equals(a.getLogin()))
                .collect(Collectors.toMap(Account::getLogin, Account::getLogin));
        return ImmutableMap.of("pack", packView, "protos", protosView, "invites", invitesView);
    }

    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public ModelAndView save(@ModelAttribute("pack") QuestPackView packView) {
        final String userId = getUserId();
        final String packId = packView.getId();

        final QuestPack questPack = questPackRepository.getById(packId);
        final Collection<String> currentProtoIds = questPack.getProtoIdsByQuestId().values();

        final List<String> errorProtoIds = new ArrayList<>();
        final List<String> protoIds = packView.getProtoIds();
        if (CollectionUtils.isNotEmpty(protoIds)) {
            for (String protoId : protoIds) {
                if (currentProtoIds.contains(protoId)) {
                    continue;
                }
                if (!questPackService.addQuest(protoId, packId, userId)) {
                    errorProtoIds.add(protoId);
                }
            }
        }

        final List<String> inviteIds = packView.getInviteIds();
        if (CollectionUtils.isNotEmpty(inviteIds)) {
            final Set<String> packOldUsers = questPack.getUserScores().keySet();
            for (String invitedId : inviteIds) {
                if (!packOldUsers.contains(invitedId)) {
                    questPackService.addUser(packId, invitedId, userId);

                }
            }
        }

        return CollectionUtils.isNotEmpty(errorProtoIds)
                ? getErrorView(ImmutableList.of("something is wrong with:" + errorProtoIds))
                : getMessageView(ImmutableList.of("pack [" + packId + "] created"));
    }

    //TODO move onto the proto controller
    @RequestMapping(value = "/edit-quest", method = RequestMethod.GET)
    public Map<String, Object> editQuest(@RequestParam("id") String protoId, @RequestParam("packId") String packId) {
        final QuestProto questProto = ObjectUtils.defaultIfNull(questProtoDao.getById(protoId), new QuestProto());
        final QuestPack questPack = ObjectUtils.defaultIfNull(questPackRepository.getById(packId), new QuestPack(null));
        final Map<String, String> nextQuestNamesById = questPack.getProtoIdsByQuestId().entrySet().stream()
                .filter(e -> !protoId.equals(e.getValue()))
                .filter(e -> Optional.ofNullable(questProtoDao.getById(e.getValue()))
                                .map(QuestProto::isActivatedByTrigger).orElse(false)
                )
                .collect(Collectors.toMap(
                        Entry::getKey,
                        e -> Optional.ofNullable(questProtoDao.getById(e.getValue()))
                                .map(QuestProto::getName).orElse(e.getValue())
                ));
        return ImmutableMap.of("questProto", questProto, "packId", packId, "nextQuest", nextQuestNamesById);
    }

    //TODO move onto the proto controller
    @RequestMapping(value = "/save-quest", method = RequestMethod.POST)
    public ModelAndView save(@ModelAttribute("questProto") QuestProtoView questProto) {
        final boolean newQuest = StringUtils.isBlank(questProto.getId());
        if (newQuest) {
            questProto.setId(UUID.randomUUID().toString());
        }
        questProtoService.validate(questProto);
        questProtoDao.save(questProto);
        if (newQuest) {
            final String packId = questProto.getPackId();
            questPackService.addQuest(questProto.getId(), packId, getUserId());
        }
        return getMessageView(ImmutableList.of("QuestProto [" + questProto.getId() + "] created"));
    }

//    @RequestMapping(value = "/user/add", method = RequestMethod.POST)
//    public ModelAndView addUser(
//            @RequestParam(name = "userId") String userId,
//            @RequestParam(name = "packId") String packId) {
//        final String currentUserId = getUserId();
//        questPackService.addUser(packId, userId, currentUserId);
//        return getDefaulView(ImmutableMap.of("messages", ImmutableList.of("User [" + userId + "] added to Pack")));
//    }

}
