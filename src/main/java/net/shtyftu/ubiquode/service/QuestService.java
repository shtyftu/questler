package net.shtyftu.ubiquode.service;

import com.lambdaworks.com.google.common.base.Throwables;
import java.time.Instant;
import java.time.ZoneOffset;
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
import net.shtyftu.ubiquode.service.QuestException.OnlyDeadlineLowScoreUserException;
import net.shtyftu.ubiquode.service.QuestException.UserUnableToLockException;
import net.shtyftu.ubiquode.service.QuestException.WrongStateException;
import org.apache.commons.collections4.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author shtyftu
 */
@Service
public class QuestService {

    private final Logger log = LoggerFactory.getLogger(this.getClass());
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

    public void lock(String questId, String userId, String packId) {
        log.debug("User [" + userId + "] locking quest/pack: " + questId + " / " + packId);
        final Quest quest = get(questId);
        log.debug("Quest found: " + quest);
        final State state = quest.getState();
        try {
            checkIfCanLock(userId, packId, state);
        } catch (QuestException e) {
            log.error("Unable to lock:", e);
            throw Throwables.propagate(e);
        }
        final long eventTime = questRepository.lock(questId, userId);
        userProjector.lock(userId, questId, eventTime);
    }

    public boolean canBeLocked(String userId, String packId, State state) {
        try {
            checkIfCanLock(userId, packId, state);
            return true;
        } catch (QuestException e) {
            log.debug("Can't be locked: ", e);
            return false;
        }
    }

    private void checkIfCanLock(String userId, String packId, State state) throws QuestException {
        if (!State.Available.equals(state) && !State.DeadlinePanic.equals(state)) {
            throw new WrongStateException();
        }

        final QuestPack questPack = questPackRepository.getById(packId);
        if (State.DeadlinePanic != state) {
            final List<Quest> deadlinePanicQuests = questPack.getProtoIdsByQuestId().keySet().stream()
                    .map(questRepository::getById)
                    .filter(Objects::nonNull)
                    .filter(q -> State.DeadlinePanic == q.getState())
                    .collect(Collectors.toList());
            if (CollectionUtils.isNotEmpty(deadlinePanicQuests) && userId.equals(questPack.getLowestScoreUser())) {
                throw new OnlyDeadlineLowScoreUserException();
            }
        }

        if (State.DeadlinePanic != state || !userId.equals(questPack.getLowestScoreUser())) {
            final User user = userProjector.getById(userId);

            final Long unableToLockQuestTill = user.getUnableToLockQuestTill();
            if (unableToLockQuestTill != null) {
                final long millis = System.currentTimeMillis();
                if (millis < unableToLockQuestTill) {
                    throw new UserUnableToLockException(
                            Instant.ofEpochSecond(millis).atOffset(ZoneOffset.UTC).toString() + " UTC"
                    );
                }
            }
        }
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
        if (proto.isActivatedByTrigger() && State.WaitingTrigger.equals(quest.getState())) {
            final Long deadline = proto.getDeadline();
            questRepository.trigger(questId, deadline);
            return true;
        }
        return false;
    }

}
