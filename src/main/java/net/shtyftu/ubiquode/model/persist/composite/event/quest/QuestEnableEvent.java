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
    public void applyTo(Quest quest) {
        quest.setEnabled(true);
        if (quest.getProto().isActivatedByTrigger()) {
            quest.setWaitTrigger(true);
        } else {
            quest.setDeadlineAt(getTime() + deadline);
        }
    }

    @Override
    public String getViewName() {
        return "Enable";
    }
}
