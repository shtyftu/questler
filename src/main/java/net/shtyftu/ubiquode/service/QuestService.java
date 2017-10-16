package net.shtyftu.ubiquode.service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import net.shtyftu.ubiquode.dao.QuestEventDao;
import net.shtyftu.ubiquode.dao.QuestProtoDao;
import net.shtyftu.ubiquode.dao.UserEventDao;
import net.shtyftu.ubiquode.event.QuestEvent;
import net.shtyftu.ubiquode.event.processor.QuestStateProcessor;
import net.shtyftu.ubiquode.model.QuestProto;
import net.shtyftu.ubiquode.model.QuestState;
import net.shtyftu.ubiquode.model.QuestState.State;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author shtyftu
 */
public class QuestService {

    private final QuestProtoDao questProtoDao;
    private final QuestEventDao questEventDao;
    private final UserEventDao userEventDao;
    private final QuestStateProcessor questStateProcessor;

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
        final QuestProto questProto = questProtoDao.getAll().stream()
                .filter(q -> q.getId().equals(questId))
                .findFirst().orElse(null);
        final QuestState questState = getQuestState(System.currentTimeMillis(), questProto);
        if (questState.getState().equals(State.Available)) {
            questEventDao.save(new QuestEvent)


        }

        return false;
    }

    private QuestState getQuestState(long time, QuestProto proto) {
        //TOD rewrite
        return new QuestState(proto, time + proto.getDeadline(), time + proto.getCooldown(), State.Available);
    }
}
