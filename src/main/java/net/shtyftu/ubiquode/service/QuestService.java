package net.shtyftu.ubiquode.service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import net.shtyftu.ubiquode.dao.plain.QuestProtoDao;
import net.shtyftu.ubiquode.model.QuestPack;
import net.shtyftu.ubiquode.model.projection.Quest;
import net.shtyftu.ubiquode.model.projection.Quest.State;
import net.shtyftu.ubiquode.model.projection.User;
import net.shtyftu.ubiquode.processor.QuestPackProcessor;
import net.shtyftu.ubiquode.processor.QuestProcessor;
import net.shtyftu.ubiquode.processor.UserProcessor;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author shtyftu
 */
@Service
public class QuestService {

    private final QuestProcessor questProcessor;
    private final QuestPackProcessor questPackProcessor;
    private final UserProcessor userProcessor;

    @Autowired
    public QuestService(QuestProcessor questProcessor,
            QuestPackProcessor questPackProcessor, UserProcessor userProcessor) {
        this.questProcessor = questProcessor;
        this.questPackProcessor = questPackProcessor;
        this.userProcessor = userProcessor;
    }

    public Map<String, List<Quest>> getAllFor(String userId) {
        final User user = userProcessor.getById(userId);
        final List<String> questPackIds = user.getQuestPackIds();
        return questPackIds.stream()
                .map(questPackProcessor::getById)
                .collect(Collectors.toMap(
                        QuestPack::getName,
                        pack -> pack.getQuestIdList().stream()
                                .map(questProcessor::getById)
                                .collect(Collectors.toList())));
    }

    public boolean lock(String questId, String userId, String packId) {
        final Quest quest = get(questId);
        final State state = quest.getState();
        if (!State.Available.equals(state) && !State.DeadlinePanic.equals(state)) {
            return false;
        }

        final QuestPack questPack = questPackProcessor.getById(packId);
        if (State.DeadlinePanic != state) {
            final List<Quest> deadlinePanicQuests = questPack.getQuestIdList().stream()
                    .map(questProcessor::getById)
                    .filter(q -> State.DeadlinePanic == q.getState())
                    .collect(Collectors.toList());
            if (CollectionUtils.isNotEmpty(deadlinePanicQuests) && userId.equals(questPack.getLowestScoreUser())) {
                return false;
            }
        }

        if (State.DeadlinePanic != state || !userId.equals(questPack.getLowestScoreUser())) {
            final User user = userProcessor.getById(userId);
            if (!user.isCanLockQuest()) {
                return false;
            }

        }
        questProcessor.lock(questId, userId);
        userProcessor.lock(userId, questId);
        return true;
    }

    public boolean complete(String questId, String userId) {
        final User user = userProcessor.getById(userId);
        if (!questId.equals(user.getLockedQuestId())) {
            return false;
        }
        if (!user.isCanCompleteQuest(questId)) {
            return false;
        }
        questProcessor.complete(questId);
        userProcessor.complete(userId, questId);
        return true;
    }

    public boolean enable(String questId) {
        final Quest quest = get(questId);
        if (State.Disabled.equals(quest.getState())) {
            questProcessor.enable(questId);
            return true;
        }
        return false;
    }

    private Quest get(String questId) {
        return questProcessor.getById(questId);
    }
}
