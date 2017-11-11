package net.shtyftu.ubiquode.model.persist.composite.event.quest;

import net.shtyftu.ubiquode.model.projection.Quest;

/**
 * @author shtyftu
 */
public class QuestCompleteEvent extends QuestEvent {

    public QuestCompleteEvent(String questId) {
        super(questId);
    }

    @Override
    public void applyTo(Quest quest) {
        quest.setLockedTill(null);
        if (quest.getProto().isActivatedByTrigger()) {
            quest.setWaitTrigger(true);
        } else {
            final long cooldownTill = getTime() + quest.getProto().getCooldown();
            quest.setCooldownTill(cooldownTill);
            quest.setDeadlineAt(cooldownTill + quest.getProto().getDeadline());
        }
    }
}
