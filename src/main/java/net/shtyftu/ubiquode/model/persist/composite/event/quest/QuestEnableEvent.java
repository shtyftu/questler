package net.shtyftu.ubiquode.model.persist.composite.event.quest;

import net.shtyftu.ubiquode.model.projection.Quest;

/**
 * @author shtyftu
 */
public class QuestEnableEvent extends QuestEvent {

    private final long deadline;

    public QuestEnableEvent(String questId, long deadline) {
        super(questId);
        this.deadline = deadline;
    }

    @Override
    public void applyTo(Quest entity) {
        entity.setEnabled(true);
        entity.setDeadlineAt(getTime() + deadline);
    }
}
