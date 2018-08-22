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
import net.shtyftu.ubiquode.processor.QuestPackRepository;
import net.shtyftu.ubiquode.processor.QuestRepository;
import net.shtyftu.ubiquode.processor.UserRepository;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author shtyftu
 */
@Service
public class QuestService {

    private final QuestProtoDao questProtoDao;
    private final QuestRepository questRepository;
    private final QuestPackRepository questPackRepository;
    private final UserRepository userProjector;

    @Autowired
    public QuestService(QuestProtoDao questProtoDao, QuestRepository questProjector,
            QuestPackRepository questPackRepository, UserRepository userProjector) {
        this.questProtoDao = questProtoDao;
        this.questRepository = questProjector;
        this.questPackRepository = questPackRepository;
        this.userProjector = userProjector;
    }

    public Map<QuestPack, List<Quest>> getAllFor(String userId) {
        final User user = userProjector.getById(userId);
        final List<String> questPackIds = user.getQuestPackIds();
        return questPackIds.stream()
                .map(questPackRepository::getById)
                .collect(Collectors.toMap(
                        pack -> pack,
                        pack -> pack.getProtoIdsByQuestId().keySet().stream()
                                .map(questRepository::getById)
                                .filter(Objects::nonNull)
                                .collect(Collectors.toList())));
    }

    public boolean lock(String questId, String userId, String packId) {
        final Quest quest = get(questId);
        final State state = quest.getState();
        if (!canBeLocked(userId, packId, state)) {
            return false;
        }
        final long eventTime = questRepository.lock(questId, userId);
        userProjector.lock(userId, questId, eventTime);
        return true;
    }

    public boolean canBeLocked(String userId, String packId, State state) {
        if (!State.Available.equals(state) && !State.DeadlinePanic.equals(state)) {
            return false;
        }

        final QuestPack questPack = questPackRepository.getById(packId);
        if (State.DeadlinePanic != state) {
            final List<Quest> deadlinePanicQuests = questPack.getProtoIdsByQuestId().keySet().stream()
                    .map(questRepository::getById)
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

        final long eventTime = questRepository.complete(questId);
        final QuestPack questPack = questPackRepository.getById(packId);
        final String protoId = questPack.getProtoIdsByQuestId().get(questId);
        final QuestProto questProto = questProtoDao.getById(protoId);
        final int scores = questProto.getScores();
        questPackRepository.addScores(packId, userId, scores, eventTime);
        userProjector.complete(userId, scores, eventTime);

        if (questProto.getNextQuestId() != null) {
            trigger(questProto.getNextQuestId());
        }
        return true;
    }

    public boolean enable(String questId, String packId) {
        final Quest quest = get(questId);
        if (State.Disabled.equals(quest.getState())) {
            final QuestPack questPack = questPackRepository.getById(packId);
            final String protoId = questPack.getProtoIdsByQuestId().get(questId);
            final QuestProto proto = questProtoDao.getById(protoId);
            final Long deadline = proto.getDeadline();
            questRepository.enable(questId, deadline);
            return true;
        }
        return false;
    }

    private Quest get(String questId) {
        return questRepository.getById(questId);
    }

    public boolean trigger(String questId) {
        final Quest quest = get(questId);
        final QuestProto proto = quest.getProto();
        if (proto.isActivatedByTrigger() && State.WaitingTrigger.equals(quest.getState())){
            final Long deadline = proto.getDeadline();
            questRepository.trigger(questId, deadline);
            return true;
        }
        return false;
    }
}
