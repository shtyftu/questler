package net.shtyftu.ubiquode.model.persist.composite.event.user;

import net.shtyftu.ubiquode.model.projection.User;

/**
 * @author shtyftu
 */
public class UserQuestCompleteEvent extends UserEvent {

    private final int scores;

    public UserQuestCompleteEvent(String userId, int scores, long eventTime) {
        super(userId, eventTime);
        this.scores = scores;
    }

    @Override
    public void applyTo(User user) {
        user.setLockedQuestId(null);
        user.setUnableToLockQuestTill(null);
        user.setMustCompleteQuestTill(null);
    }
}
