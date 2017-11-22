package net.shtyftu.ubiquode.controller;

import static net.shtyftu.ubiquode.controller.AController.QUEST_CONTROLLER_PATH;

import com.google.common.collect.ImmutableMap;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import net.shtyftu.ubiquode.model.QuestPack;
import net.shtyftu.ubiquode.model.projection.Quest;
import net.shtyftu.ubiquode.model.view.QuestScoresView;
import net.shtyftu.ubiquode.model.view.QuestView;
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
        super();
        this.questService = questService;
    }

    @Override
    protected Map<String, Object> getDefaultViewModel() {
        final String userId = getUserId();
        final Map<QuestPack, List<Quest>> quests = questService.getAllFor(userId);
        final List<QuestView> questListView = quests.entrySet().stream()
                .map(e -> {
                    final QuestPack pack = e.getKey();
                    return e.getValue().stream()
                            .map(q -> {
                                boolean canBeLocked = questService.canBeLocked(userId, pack.getId(), q.getState());
                                return new QuestView(userId, q, pack, canBeLocked);
                            })
                            .collect(Collectors.toList());
                })
                .flatMap(Collection::stream)
                .sorted()
                .collect(Collectors.toList());

        final List<QuestScoresView> scoresListView = quests.keySet().stream()
                .map(QuestScoresView::new)
                .sorted(Comparator.comparing(QuestScoresView::getPackName))
                .collect(Collectors.toList());

        return ImmutableMap.of("questList", questListView, "scoresList",  scoresListView);
    }

    @RequestMapping(path = LIST_PATH, method = RequestMethod.GET)
    public Map<String, Object> getList() {
        return getDefaultViewModel();
    }

    @RequestMapping(value ="/enable", method = RequestMethod.POST)
    public String enable(
            @RequestParam(name = "questId") String questId,
            @RequestParam(name = "packId") String packId) {
        boolean result = questService.enable(questId, packId);
        return "redirect:/quest" + LIST_PATH;
    }

    @RequestMapping(value ="/trigger", method = RequestMethod.POST)
    public String trigger(
            @RequestParam(name = "questId") String questId) {
        boolean result = questService.trigger(questId);
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
        boolean result = questService.complete(questId, userId, packId);
        return "redirect:/quest" + LIST_PATH;
    }

}
