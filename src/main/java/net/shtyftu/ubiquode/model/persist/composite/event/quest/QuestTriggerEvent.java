package net.shtyftu.ubiquode.model.persist.composite.event.quest;

import net.shtyftu.ubiquode.model.projection.Quest;

/**
 * @author shtyftu
 */
public class QuestTriggerEvent extends QuestEvent {

    private final long deadline;


    public QuestTriggerEvent(String questId, long deadline) {
        super(questId);
        this.deadline = deadline;
    }

    @Override
    public void applyTo(Quest quest) {
        if (quest.getProto().isActivatedByTrigger() && quest.isWaitTrigger()) {
            quest.setWaitTrigger(false);
            quest.setDeadlineAt(getTime() + deadline);
        }
    }

    @Override
    public String getViewName() {
        return "Trigger";
    }
}
