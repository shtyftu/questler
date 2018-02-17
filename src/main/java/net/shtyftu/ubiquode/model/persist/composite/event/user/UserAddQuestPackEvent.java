package net.shtyftu.ubiquode.model.persist.composite.event.user;

import java.util.List;
import net.shtyftu.ubiquode.model.projection.User;

/**
 * @author shtyftu
 */
public class UserAddQuestPackEvent extends UserEvent {

    private final String packId;
    private final String inviterUserId;

    public UserAddQuestPackEvent(String userId, String packId, String inviterUserId, long eventTime) {
        super(userId, eventTime);
        this.packId = packId;
        this.inviterUserId = inviterUserId;
    }

    @Override
    public void applyTo(User user) {
        final List<String> questPackIds = user.getQuestPackIds();
        questPackIds.add(packId);
    }

    public String getPackId() {
        return packId;
    }
}
