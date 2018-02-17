package net.shtyftu.ubiquode.model.persist.composite.event.user;

import net.shtyftu.ubiquode.model.projection.User;
import net.shtyftu.ubiquode.service.ConfigService;

/**
 * @author shtyftu
 */
public class UserQuestLockEvent extends UserEvent {

    private final String questId;

    public UserQuestLockEvent(String userId, String questId, long eventTime) {
        super(userId, eventTime);
        this.questId = questId;
    }

    @Override
    public void applyTo(User user) {
        final long now = System.currentTimeMillis();
        user.setLockedQuestId(questId);
        final long mustCompleteQuestTill = now + ConfigService.QUEST_LOCK_TIME;
        user.setMustCompleteQuestTill(mustCompleteQuestTill);
        user.setUnableToLockQuestTill(mustCompleteQuestTill + ConfigService.UNCOMPLETED_LOCK_PENALTY_TIME);
    }
}
