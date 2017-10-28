package net.shtyftu.ubiquode.model.persist.composite.event.user;

import java.util.List;
import java.util.UUID;
import net.shtyftu.ubiquode.model.projection.User;

/**
 * @author shtyftu
 */
public class UserAddQuestPackEvent extends UserEvent {

    private final String packId;
    private final String inviterUserId;

    public UserAddQuestPackEvent(String userId, String packId, String inviterUserId) {
        super(userId);
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
