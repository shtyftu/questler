package net.shtyftu.ubiquode.service;

import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;
import net.shtyftu.ubiquode.dao.composite.event.QuestEventDao;
import net.shtyftu.ubiquode.dao.simple.QuestProtoDao;
import net.shtyftu.ubiquode.dao.composite.event.UserEventDao;
import net.shtyftu.ubiquode.model.persist.composite.event.quest.QuestLockEvent;
import net.shtyftu.ubiquode.model.persist.composite.event.user.UserQuestLockEvent;
import net.shtyftu.ubiquode.processor.QuestStateProcessor;
import net.shtyftu.ubiquode.model.projection.QuestState;
import net.shtyftu.ubiquode.model.projection.QuestState.State;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author shtyftu
 */
public class QuestService {

    private static final long LOCK_TIME_MINUTES = TimeUnit.HOURS.toMinutes(3);

    private final QuestProtoDao questProtoDao;
    private final QuestEventDao questEventDao;
    private final UserEventDao userEventDao;
    private final QuestStateProcessor questStateProcessor;

    private final ScheduledExecutorService executor = Executors.newScheduledThreadPool(1, r -> {
        Thread t = new Thread(r);
        t.setDaemon(true);
        t.setName(QuestService.class + "-executor-thread");
        return t;
    });

    @Autowired
    public QuestService(QuestProtoDao questProtoDao, QuestEventDao questEventDao, UserEventDao userEventDao,
            QuestStateProcessor questStateProcessor) {
        this.questProtoDao = questProtoDao;
        this.questEventDao = questEventDao;
        this.userEventDao = userEventDao;
        this.questStateProcessor = questStateProcessor;
    }

    public List<QuestState> getAll() {
        return questProtoDao.getAllKeys().stream()
                .map(questStateProcessor::getByKey)
                .collect(Collectors.toList());
    }

    public boolean lock(String questId, String userId) {
        final QuestState questState = questStateProcessor.getByKey(questId);
        if (questState.getState().equals(State.Available)) {
            questEventDao.save(new QuestLockEvent(questId, userId));
            userEventDao.save(new UserQuestLockEvent(userId, questId));
            executor.schedule(() -> unlock(questId, userId), LOCK_TIME_MINUTES, TimeUnit.MINUTES);
            return true;
        }
        return false;
    }

    private void unlock(String questId, String userId) {

    }

}
