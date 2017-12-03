package net.shtyftu.ubiquode.controller;

import static net.shtyftu.ubiquode.controller.AController.PACK_CONTROLLER_PATH;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;
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
import net.shtyftu.ubiquode.processor.QuestPackProcessor;
import net.shtyftu.ubiquode.processor.QuestProcessor;
import net.shtyftu.ubiquode.processor.UserProcessor;
import net.shtyftu.ubiquode.service.QuestPackService;
import net.shtyftu.ubiquode.service.QuestProtoService;
import org.apache.commons.collections4.CollectionUtils;
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
    private final UserProcessor userProcessor;
    private final QuestPackProcessor questPackProcessor;
    private final QuestProcessor questProcessor;
    private final QuestProtoService questProtoService;
    private final QuestPackService questPackService;
    private final AccountDao accountDao;

    @Autowired
    public QuestPackController(QuestProtoDao questProtoDao, UserProcessor userProcessor,
            QuestPackProcessor questPackProcessor, QuestProcessor questProcessor,
           QuestProtoService questProtoService, QuestPackService questPackService, AccountDao accountDao) {
        this.questProtoDao = questProtoDao;
        this.userProcessor = userProcessor;
        this.questPackProcessor = questPackProcessor;
        this.questProcessor = questProcessor;
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
        final User user = userProcessor.getById(userId);
        final List<String> questPackIds = user.getQuestPackIds();
        final List<QuestPackLightView> view = questPackIds.stream()
                .map(questPackProcessor::getById)
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
        return getDefaulView(ImmutableMap.of("messages", ImmutableList.of("pack [" + packId + "] created")));
    }

    @RequestMapping(value = EDIT_PATH, method = RequestMethod.GET)
    public Map<String, Object> edit(@RequestParam(name = "packId") String packId) {
        final QuestPack pack = questPackProcessor.getById(packId);
        final Set<String> questIds = pack.getProtoIdsByQuestId().keySet();
        final Map<String, String> questNamesById = questIds.stream()
                .map(questProcessor::getById)
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

        final QuestPack questPack = questPackProcessor.getById(packId);
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

        if (CollectionUtils.isNotEmpty(errorProtoIds)) {
            return getDefaulView(ImmutableMap.of("errors",
                    ImmutableList.of("something is wrong with:" + errorProtoIds)));
        } else {
            return getDefaulView(ImmutableMap.of("messages", ImmutableList.of("pack [" + packId + "] created")));
        }
    }

    //TODO move onto the proto controller
    @RequestMapping(value = "/edit-quest", method = RequestMethod.GET)
    public Map<String, Object> editQuest(@RequestParam("id") String protoId) {
        final QuestProto questProto = questProtoDao.getById(protoId);
        return ImmutableMap.of("questProto", questProto);
    }

    //TODO move onto the proto controller
    @RequestMapping(value = "/save-quest", method = RequestMethod.POST)
    public ModelAndView save(@ModelAttribute("questProto") QuestProto questProto) {
        questProtoService.validate(questProto);
        questProtoDao.save(questProto);
        return getDefaulView(ImmutableMap.of(
                "messages",
                ImmutableList.of("QuestProto [" + questProto.getId() + "] created")));
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
