package net.shtyftu.ubiquode.service;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;
import net.shtyftu.ubiquode.dao.plain.QuestProtoDao;
import net.shtyftu.ubiquode.model.QuestPack;
import net.shtyftu.ubiquode.model.persist.simple.QuestProto;
import net.shtyftu.ubiquode.model.projection.Quest;
import net.shtyftu.ubiquode.model.projection.Quest.State;
import net.shtyftu.ubiquode.model.projection.User;
import net.shtyftu.ubiquode.processor.QuestPackProjector;
import net.shtyftu.ubiquode.processor.QuestProjector;
import net.shtyftu.ubiquode.processor.UserProjector;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author shtyftu
 */
@Service
public class QuestService {

    private final QuestProtoDao questProtoDao;
    private final QuestProjector questProjector;
    private final QuestPackProjector questPackProjector;
    private final UserProjector userProjector;

    @Autowired
    public QuestService(QuestProtoDao questProtoDao, QuestProjector questProjector,
            QuestPackProjector questPackProjector, UserProjector userProjector) {
        this.questProtoDao = questProtoDao;
        this.questProjector = questProjector;
        this.questPackProjector = questPackProjector;
        this.userProjector = userProjector;
    }

    public Map<QuestPack, List<Quest>> getAllFor(String userId) {
        final User user = userProjector.getById(userId);
        final List<String> questPackIds = user.getQuestPackIds();
        return questPackIds.stream()
                .map(questPackProjector::getById)
                .collect(Collectors.toMap(
                        pack -> pack,
                        pack -> pack.getProtoIdsByQuestId().keySet().stream()
                                .map(questProjector::getById)
                                .filter(Objects::nonNull)
                                .collect(Collectors.toList())));
    }

    public boolean lock(String questId, String userId, String packId) {
        final Quest quest = get(questId);
        final State state = quest.getState();
        if (!canBeLocked(userId, packId, state)) {
            return false;
        }
        questProjector.lock(questId, userId);
        userProjector.lock(userId, questId);
        return true;
    }

    public boolean canBeLocked(String userId, String packId, State state) {
        if (!State.Available.equals(state) && !State.DeadlinePanic.equals(state)) {
            return false;
        }

        final QuestPack questPack = questPackProjector.getById(packId);
        if (State.DeadlinePanic != state) {
            final List<Quest> deadlinePanicQuests = questPack.getProtoIdsByQuestId().keySet().stream()
                    .map(questProjector::getById)
                    .filter(Objects::nonNull)
                    .filter(q -> State.DeadlinePanic == q.getState())
                    .collect(Collectors.toList());
            if (CollectionUtils.isNotEmpty(deadlinePanicQuests) && userId.equals(questPack.getLowestScoreUser())) {
                return false;
            }
        }

        if (State.DeadlinePanic != state || !userId.equals(questPack.getLowestScoreUser())) {
            final User user = userProjector.getById(userId);
            if (!user.isCanLockQuest()) {
                return false;
            }

        }
        return true;
    }

    public boolean complete(String questId, String userId, String packId) {
        final User user = userProjector.getById(userId);
        if (!questId.equals(user.getLockedQuestId())) {
            return false;
        }
        if (!user.isCanCompleteQuest(questId)) {
            return false;
        }

        questProjector.complete(questId);
        final QuestPack questPack = questPackProjector.getById(packId);
        final String protoId = questPack.getProtoIdsByQuestId().get(questId);
        final QuestProto questProto = questProtoDao.getById(protoId);
        final int scores = questProto.getScores();
        userProjector.complete(userId, scores);
        questPackProjector.addScores(packId, userId, scores);
        return true;
    }

    public boolean enable(String questId, String packId) {
        final Quest quest = get(questId);
        if (State.Disabled.equals(quest.getState())) {
            final QuestPack questPack = questPackProjector.getById(packId);
            final String protoId = questPack.getProtoIdsByQuestId().get(questId);
            final QuestProto proto = questProtoDao.getById(protoId);
            final Long deadline = proto.getDeadline();
            questProjector.enable(questId, deadline);
            return true;
        }
        return false;
    }

    private Quest get(String questId) {
        return questProjector.getById(questId);
    }

    public boolean trigger(String questId) {
        final Quest quest = get(questId);
        final QuestProto proto = quest.getProto();
        if (proto.isActivatedByTrigger() && State.WaitingTrigger.equals(quest.getState())){
            final Long deadline = proto.getDeadline();
            questProjector.trigger(questId, deadline);
            return true;
        }
        return false;
    }
}
