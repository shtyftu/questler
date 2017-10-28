package net.shtyftu.ubiquode.controller;

import static net.shtyftu.ubiquode.controller.AController.QUEST_CONTROLLER_PATH;

import com.google.common.collect.ImmutableMap;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import net.shtyftu.ubiquode.model.projection.Quest;
import net.shtyftu.ubiquode.model.view.UserQuestView;
import net.shtyftu.ubiquode.service.QuestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author shtyftu
 */
@Controller
@RequestMapping(QUEST_CONTROLLER_PATH)
public class QuestController extends AController {

    private final QuestService questService;

    @Autowired
    public QuestController(QuestService questService) {
        this.questService = questService;
    }

    @RequestMapping(path = LIST_PATH, method = RequestMethod.GET)
    public Map<String, Object> getList() {
        final String userId = getUserId();
        final Map<String, List<Quest>> quests = questService.getAllFor(userId);
        final List<UserQuestView> view = quests.entrySet().stream()
                .map(e -> {
                    final String packName = e.getKey();
                    return e.getValue().stream()
                            .map(q -> new UserQuestView(q, userId, packName))
                            .collect(Collectors.toList());
                })
                .flatMap(Collection::stream)
                .sorted()
                .collect(Collectors.toList());

        return ImmutableMap.of("list", view);
    }

    @RequestMapping(value ="/enable", method = RequestMethod.POST)
    public String enable(
            @RequestParam(name = "questId") String questId,
            @RequestParam(name = "packId") String packId) {
        boolean result = questService.enable(questId);
        return "redirect:/quest" + LIST_PATH;
    }

    @RequestMapping(value="/lock", method = RequestMethod.POST)
    public String lock(
            @RequestParam(name = "questId") String questId,
            @RequestParam(name = "packId") String packId) {
        final String userId = getUserId();
        boolean result = questService.lock(questId, userId, packId);
        return "redirect:/quest" + LIST_PATH;
    }

    @RequestMapping(value = "/complete", method = RequestMethod.POST)
    public String complete(
            @RequestParam(name = "questId") String questId,
            @RequestParam(name = "packId") String packId) {
        final String userId = getUserId();
        boolean result = questService.complete(questId, userId);
        return "redirect:/quest" + LIST_PATH;
    }

}
