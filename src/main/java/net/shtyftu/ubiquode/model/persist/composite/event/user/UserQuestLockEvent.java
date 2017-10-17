package net.shtyftu.ubiquode.model.persist.composite.event.user;

import net.shtyftu.ubiquode.model.persist.simple.User;

/**
 * @author shtyftu
 */
public class UserQuestLockEvent extends UserEvent {

    final String questId;

    public UserQuestLockEvent(String userId, String questId) {
        super(userId);
        this.questId = questId;
    }

    @Override
    public void applyTo(User user) {
        user.setLockedQuestId(questId);
    }
}
