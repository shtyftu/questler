package net.shtyftu.ubiquode.model.persist.composite.event.questPack;

import net.shtyftu.ubiquode.model.QuestPack;

/**
 * @author shtyftu
 */
public class QuestPackAddUserEvent extends QuestPackEvent {

    private final String userId;
    private final String inviterId;

    public QuestPackAddUserEvent(String packId, String userId, String inviterId) {
        super(packId);
        this.userId = userId;
        this.inviterId = inviterId;
    }

    @Override
    public void applyTo(QuestPack questPack) {
        questPack.getUserScores().put(userId, 0);
    }
}
