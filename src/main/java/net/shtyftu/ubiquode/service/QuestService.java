package net.shtyftu.ubiquode.service;

import java.util.List;
import java.util.stream.Collectors;
import net.shtyftu.ubiquode.dao.simple.QuestProtoDao;
import net.shtyftu.ubiquode.model.projection.User;
import net.shtyftu.ubiquode.model.projection.QuestState;
import net.shtyftu.ubiquode.model.projection.QuestState.State;
import net.shtyftu.ubiquode.processor.QuestStateProcessor;
import net.shtyftu.ubiquode.processor.UserProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author shtyftu
 */
@Service
public class QuestService {

    private final QuestProtoDao questProtoDao;
    private final QuestStateProcessor questStateProcessor;
    private final UserProcessor userProcessor;

    @Autowired
    public QuestService(
            QuestProtoDao questProtoDao,
            QuestStateProcessor questStateProcessor,
            UserProcessor userProcessor) {
        this.questProtoDao = questProtoDao;
        this.questStateProcessor = questStateProcessor;
        this.userProcessor = userProcessor;
    }

    public List<QuestState> getAll() {
        return questProtoDao.getAllKeys().stream()
                .map(questStateProcessor::getByKey)
                .collect(Collectors.toList());
    }

    public boolean lock(String questId, String userId) {
        final QuestState questState = questStateProcessor.getByKey(questId);
        final State state = questState.getState();
        if (!State.Available.equals(state) && !State.DeadlinePanic.equals(state)) {
            return false;
        }
        final User user = userProcessor.getByKey(userId);
        if (user.isNeedToDealWithDeadline() && !State.DeadlinePanic.equals(state)
                && getAll().stream().anyMatch(q -> State.DeadlinePanic.equals(q.getState()))) {
            return false;
        }
        if (!user.isCanLockQuest()) {
            return false;
        }
        questStateProcessor.lock(questId, userId);
        userProcessor.lock(userId, questId);
        return true;
    }

    public boolean complete(String questId, String userId) {
        final User user = userProcessor.getByKey(userId);
        if (!questId.equals(user.getLockedQuestId())) {
            return false;
        }
        if (!user.isCanCompleteQuest(questId)) {
            return false;
        }
        questStateProcessor.complete(questId);
        userProcessor.complete(userId, questId);
        return true;
    }

}
