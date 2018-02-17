package net.shtyftu.ubiquode.service;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.TreeMap;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import javax.annotation.Nonnull;
import net.shtyftu.ubiquode.dao.list.event.QuestEventDao;
import net.shtyftu.ubiquode.dao.list.event.UserEventDao;
import net.shtyftu.ubiquode.dao.plain.QuestProtoDao;
import net.shtyftu.ubiquode.model.QuestPack;
import net.shtyftu.ubiquode.model.persist.composite.event.AEvent;
import net.shtyftu.ubiquode.model.persist.composite.event.quest.QuestCompleteEvent;
import net.shtyftu.ubiquode.model.persist.composite.event.quest.QuestEvent;
import net.shtyftu.ubiquode.model.persist.composite.event.quest.QuestLockEvent;
import net.shtyftu.ubiquode.model.persist.simple.QuestProto;
import net.shtyftu.ubiquode.model.view.QuestEventView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

/**
 * @author shtyftu
 */
@Controller
public class QuestStatsService {

    private static final ImmutableSet<Class<? extends QuestEvent>> PACK_STATS_EVENT_CLASSES = ImmutableSet
            .of(QuestLockEvent.class, QuestCompleteEvent.class);
    private final QuestEventDao questEventDao;
    private final QuestProtoDao questProtoDao;
    private final UserEventDao userEventDao;

    @Autowired
    public QuestStatsService(QuestEventDao questEventDao, QuestProtoDao questProtoDao, UserEventDao userEventDao) {
        this.questEventDao = questEventDao;
        this.questProtoDao = questProtoDao;
        this.userEventDao = userEventDao;
    }

    public List<QuestEventView> getQuestEventViews(@Nonnull QuestPack questPack, long delta) {
        final long threshold = System.currentTimeMillis() - delta;
        final TreeMap<Long, QuestEventView> eventViewsMap = new TreeMap<>(Collections.reverseOrder());
        final Map<String, Set<Long>> eventTimesByUserId = questPack.getUserScores().keySet().stream()
                .collect(Collectors.toMap(
                        userId -> userId,
                        user -> userEventDao.getAll(user).stream().map(AEvent::getTime).collect(Collectors.toSet())));
        questPack.getProtoIdsByQuestId().forEach((questId, protoId) -> {
            final List<QuestEvent> questEvents = questEventDao.getAll(questId).stream()
                    .filter(event -> PACK_STATS_EVENT_CLASSES.contains(event.getClass()))
                    .collect(Collectors.toList());
            final int eventsCount = questEvents.size();
            IntStream.range(0, eventsCount)
                    .filter(i -> {
                        final QuestEvent event = questEvents.get(i);
                        if (event.getTime() < threshold) {
                            return false;
                        }
                        if (event instanceof QuestLockEvent && (i + 1 < eventsCount)) {
                            final QuestEvent nextEvent = questEvents.get(i + 1);
                            if (nextEvent instanceof QuestCompleteEvent && event.getId().equals(nextEvent.getId())) {
                                return false;
                            }
                        }
                        return true;
                    })
                    .forEach(i -> {
                        final QuestEvent event = questEvents.get(i);
                        final QuestProto proto = questProtoDao.getById(protoId);
                        final String eventUser = eventTimesByUserId.entrySet().stream()
                                .filter(e ->
                                        e.getValue().stream()
                                                .anyMatch(eventTime -> Math.abs(eventTime - event.getTime()) < 1000)
                                )
                                .findFirst()
                                .map(Entry::getKey).orElse("?");
                        eventViewsMap.put(event.getTime(), new QuestEventView(event, proto.getName(), eventUser));
                    });
        });
        return ImmutableList.copyOf(eventViewsMap.values());
    }

}
