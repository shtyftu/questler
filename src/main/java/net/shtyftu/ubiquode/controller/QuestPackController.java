package net.shtyftu.ubiquode.controller;

import static net.shtyftu.ubiquode.controller.AController.PACK_CONTROLLER_PATH;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import net.shtyftu.ubiquode.dao.plain.QuestProtoDao;
import net.shtyftu.ubiquode.model.QuestPack;
import net.shtyftu.ubiquode.model.persist.simple.QuestProto;
import net.shtyftu.ubiquode.model.projection.Quest;
import net.shtyftu.ubiquode.model.projection.User;
import net.shtyftu.ubiquode.model.view.QuestPackLightView;
import net.shtyftu.ubiquode.model.view.QuestPackView;
import net.shtyftu.ubiquode.processor.QuestPackProcessor;
import net.shtyftu.ubiquode.processor.QuestProcessor;
import net.shtyftu.ubiquode.processor.UserProcessor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
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

    @Autowired
    public QuestPackController(QuestProtoDao questProtoDao, UserProcessor userProcessor,
            QuestPackProcessor questPackProcessor, QuestProcessor questProcessor) {
        this.questProtoDao = questProtoDao;
        this.userProcessor = userProcessor;
        this.questPackProcessor = questPackProcessor;
        this.questProcessor = questProcessor;
    }

    @RequestMapping(value = LIST_PATH, method = RequestMethod.GET)
    public Map<String, Object> getList() {
        final String userId = getUserId();
        final User user = userProcessor.getById(userId);
        final List<String> questPackIds = user.getQuestPackIds();
        final List<QuestPackLightView> view = questPackIds.stream()
                .map(questPackProcessor::getById)
                .map(QuestPackLightView::new)
                .collect(Collectors.toList());
        return ImmutableMap.of("list", view);
    }

    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public ModelAndView create(@RequestParam(name = "name") String name) {
        final String userId = getUserId();
        final String packId = userProcessor.addNewQuestPack(userId);
        questPackProcessor.setName(packId, name);
        final ImmutableMap<String, ImmutableList<String>> view = ImmutableMap.of(
                "messages", ImmutableList.of("pack [" + packId + "] created"));
        return new ModelAndView(PACK_CONTROLLER_PATH + LIST_PATH, view);
    }

    @RequestMapping(value = EDIT_PATH, method = RequestMethod.POST)
    public Map<String, Object> edit(@RequestParam(name = "packId") String packId) {
        final QuestPack pack = questPackProcessor.getById(packId);
        final List<String> questIdList = pack.getQuestIdList();
        final Map<String, String> questNamesById = questIdList.stream().map(questProcessor::getById)
                .collect(Collectors.toMap(
                        Quest::getId,
                        q -> q.getProto().getName()));
        final Map<String, String> protosView = questProtoDao.getAll().stream()
                .collect(Collectors.toMap(QuestProto::getId, QuestProto::getName));

        final QuestPackView packView = new QuestPackView(pack, questNamesById);
        return ImmutableMap.of("pack", packView, "protos", protosView);
    }

    @RequestMapping(value = "/quest/add", method = RequestMethod.POST)
    public ModelAndView addQuest(
            @RequestParam(name = "protoId") String protoId,
            @RequestParam(name = "packId") String packId) {
        final String userId = getUserId();
        final String questId = questPackProcessor.addQuest(packId, protoId, userId);
        if (StringUtils.isBlank(questId)) {
            return new ModelAndView(
                    PACK_CONTROLLER_PATH + LIST_PATH,
                    ImmutableMap.of("errors", ImmutableList.of("something is wrong")));
        }
        questProcessor.setProtoId(questId, protoId);
        return new ModelAndView(
                PACK_CONTROLLER_PATH + LIST_PATH,
                ImmutableMap.of("messages", ImmutableList.of("pack [" + packId + "] created")));

    }

    @RequestMapping(value = "/user/add", method = RequestMethod.POST)
    public ModelAndView addUser(
            @RequestParam(name = "userId") String userId,
            @RequestParam(name = "packId") String packId) {
        final String currentUserId = getUserId();
        userProcessor.addQuestPack(userId, packId, currentUserId);
        final Map<String, ImmutableList<String>> view = ImmutableMap.of(
                "messages", ImmutableList.of("pack [" + packId + "] created"));
        return new ModelAndView(PACK_CONTROLLER_PATH + LIST_PATH, view);
    }

}
