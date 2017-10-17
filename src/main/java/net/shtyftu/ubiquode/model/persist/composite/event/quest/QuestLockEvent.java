package net.shtyftu.ubiquode.model.persist.composite.event.quest;

import net.shtyftu.ubiquode.model.projection.QuestState;
import net.shtyftu.ubiquode.model.projection.QuestState.State;

/**
 * @author shtyftu
 */
public class QuestLockEvent extends QuestEvent {

    private final String userId;

    public QuestLockEvent(String questId, String userId) {
        super(questId);
        this.userId = userId;
    }

    @Override
    public void applyTo(QuestState questState) {
        questState.setState(State.LockedByUser);
    }

}
