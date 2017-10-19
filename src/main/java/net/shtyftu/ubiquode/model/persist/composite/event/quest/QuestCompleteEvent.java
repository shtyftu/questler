package net.shtyftu.ubiquode.model.persist.composite.event.quest;

import net.shtyftu.ubiquode.model.projection.QuestState;

/**
 * @author shtyftu
 */
public class QuestCompleteEvent extends QuestEvent {

    public QuestCompleteEvent(String questId) {
        super(questId);
    }

    @Override
    public void applyTo(QuestState entity) {
        entity.setLockedTill(null);
        final long cooldownTill = now() + entity.getProto().getCooldown();
        entity.setCooldownTill(cooldownTill);
        entity.setDeadlineAt(cooldownTill + entity.getProto().getDeadline());


    }
}
