package net.shtyftu.ubiquode.controller;

import static net.shtyftu.ubiquode.controller.AController.STATS_CONTROLLER_PATH;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;
import net.shtyftu.ubiquode.model.QuestPack;
import net.shtyftu.ubiquode.model.projection.User;
import net.shtyftu.ubiquode.processor.QuestPackProjector;
import net.shtyftu.ubiquode.processor.UserProjector;
import net.shtyftu.ubiquode.service.QuestStatsService;
import net.shtyftu.ubiquode.model.view.QuestEventView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * @author shytyftu
 */
@Controller
@RequestMapping(STATS_CONTROLLER_PATH)
public class StatsController extends AController {

    private final UserProjector userProjector;
    private final QuestPackProjector questPackProjector;
    private final QuestStatsService questStatsService;

    @Autowired
    public StatsController(UserProjector userProjector, QuestPackProjector questPackProjector,
            QuestStatsService questStatsService) {
        this.userProjector = userProjector;
        this.questPackProjector = questPackProjector;
        this.questStatsService = questStatsService;
    }

    @RequestMapping(value = LIST_PATH, method = RequestMethod.GET)
    public Map<String, Object> getList() {
        return getDefaultViewModel();
    }

    @Override
    protected Map<String, Object> getDefaultViewModel() {
        final String userId = getUserId();
        final User user = userProjector.getById(userId);
        if (user == null) {
            return getErrorsViewMap(ImmutableList.of("User with name " + userId + " not found"));
        }



        final List<String> questPackIds = user.getQuestPackIds();

        final List<QuestPackEventsView> packViews = questPackIds.stream()
                .map(packId -> {
                    final QuestPack questPack = questPackProjector.getById(packId);
                    final List<QuestEventView> eventViews = questStatsService.getQuestEventViews(questPack);
                    return new QuestPackEventsView(questPack.getName(), eventViews);
                })
                .collect(Collectors.toList());
        return ImmutableMap.of("packs", packViews);
    }


    public static class QuestPackEventsView {

        private final String name;
        private final List<QuestEventView> events;


        public QuestPackEventsView(String name, List<QuestEventView> events) {
            this.name = name;
            this.events = events;
        }

        public String getName() {
            return name;
        }

        public List<QuestEventView> getEvents() {
            return events;
        }
    }


}
