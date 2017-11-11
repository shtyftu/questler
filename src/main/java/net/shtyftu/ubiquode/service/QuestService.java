package net.shtyftu.ubiquode.service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import net.shtyftu.ubiquode.dao.plain.QuestProtoDao;
import net.shtyftu.ubiquode.model.QuestPack;
import net.shtyftu.ubiquode.model.persist.simple.QuestProto;
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

    private final QuestProtoDao questProtoDao;
    private final QuestProcessor questProcessor;
    private final QuestPackProcessor questPackProcessor;
    private final UserProcessor userProcessor;

    @Autowired
    public QuestService(QuestProtoDao questProtoDao, QuestProcessor questProcessor,
            QuestPackProcessor questPackProcessor, UserProcessor userProcessor) {
        this.questProtoDao = questProtoDao;
        this.questProcessor = questProcessor;
        this.questPackProcessor = questPackProcessor;
        this.userProcessor = userProcessor;
    }

    public Map<QuestPack, List<Quest>> getAllFor(String userId) {
        final User user = userProcessor.getById(userId);
        final List<String> questPackIds = user.getQuestPackIds();
        return questPackIds.stream()
                .map(questPackProcessor::getById)
                .collect(Collectors.toMap(
                        pack -> pack,
                        pack -> pack.getProtoIdsByQuestId().keySet().stream()
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
            final List<Quest> deadlinePanicQuests = questPack.getProtoIdsByQuestId().keySet().stream()
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

    public boolean complete(String questId, String userId, String packId) {
        final User user = userProcessor.getById(userId);
        if (!questId.equals(user.getLockedQuestId())) {
            return false;
        }
        if (!user.isCanCompleteQuest(questId)) {
            return false;
        }

        questProcessor.complete(questId);
        final QuestPack questPack = questPackProcessor.getById(packId);
        final String protoId = questPack.getProtoIdsByQuestId().get(questId);
        final QuestProto questProto = questProtoDao.getById(protoId);
        final int scores = questProto.getScores();
        userProcessor.complete(userId, scores);
        questPackProcessor.addScores(packId, userId, scores);
        return true;
    }

    public boolean enable(String questId, String packId) {
        final Quest quest = get(questId);
        if (State.Disabled.equals(quest.getState())) {
            final QuestPack questPack = questPackProcessor.getById(packId);
            final String protoId = questPack.getProtoIdsByQuestId().get(questId);
            final QuestProto proto = questProtoDao.getById(protoId);
            final Long deadline = proto.getDeadline();
            questProcessor.enable(questId, deadline);
            return true;
        }
        return false;
    }

    private Quest get(String questId) {
        return questProcessor.getById(questId);
    }

    public boolean trigger(String questId) {
        final Quest quest = get(questId);
        final QuestProto proto = quest.getProto();
        if (proto.isActivatedByTrigger() && State.WaitingTrigger.equals(quest.getState())){
            final Long deadline = proto.getDeadline();
            questProcessor.trigger(questId, deadline);
            return true;
        }
        return false;
    }
}
