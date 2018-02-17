package net.shtyftu.ubiquode.service;

import com.google.common.collect.ImmutableList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.TreeMap;
import java.util.stream.Collectors;
import javax.annotation.Nonnull;
import net.shtyftu.ubiquode.dao.list.event.QuestEventDao;
import net.shtyftu.ubiquode.dao.list.event.UserEventDao;
import net.shtyftu.ubiquode.dao.plain.QuestProtoDao;
import net.shtyftu.ubiquode.model.QuestPack;
import net.shtyftu.ubiquode.model.persist.composite.event.AEvent;
import net.shtyftu.ubiquode.model.persist.simple.QuestProto;
import net.shtyftu.ubiquode.model.view.QuestEventView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

/**
 * @author shtyftu
 */
@Controller
public class QuestStatsService {

    private final QuestEventDao questEventDao;
    private final QuestProtoDao questProtoDao;
    private final UserEventDao userEventDao;

    @Autowired
    public QuestStatsService(QuestEventDao questEventDao, QuestProtoDao questProtoDao, UserEventDao userEventDao) {
        this.questEventDao = questEventDao;
        this.questProtoDao = questProtoDao;
        this.userEventDao = userEventDao;
    }

    public List<QuestEventView> getQuestEventViews(@Nonnull QuestPack questPack) {
//        final long now = System.currentTimeMillis();
//        final long statsDuration = TimeUnit.DAYS.toMillis(14);
//        final long threshold = now - statsDuration;
        final long threshold = 0;

        final TreeMap<Long, QuestEventView> eventViewsMap = new TreeMap<>(Collections.reverseOrder());
        final Map<String, Set<Long>> eventTimesByUserId = questPack.getUserScores().keySet().stream()
                .collect(Collectors.toMap(
                        userId -> userId,
                        user -> userEventDao.getAll(user).stream().map(AEvent::getTime).collect(Collectors.toSet())));
        questPack.getProtoIdsByQuestId().forEach((questId, protoId) ->
                questEventDao.getAll(questId).forEach(event -> {
                    if (event.getTime() < threshold) {
                        return;
                    }
                    final QuestProto proto = questProtoDao.getById(protoId);
                    final String eventUser = eventTimesByUserId.entrySet().stream()
                            .filter(e ->
                                    e.getValue().stream()
                                            .anyMatch(eventTime -> Math.abs(eventTime - event.getTime()) < 3000)
                            )
                            .findFirst()
                            .map(Entry::getKey).orElse("?");
                    eventViewsMap.put(event.getTime(), new QuestEventView(event, proto.getName(), eventUser));

                })
        );
        return ImmutableList.copyOf(eventViewsMap.values());
    }

}
