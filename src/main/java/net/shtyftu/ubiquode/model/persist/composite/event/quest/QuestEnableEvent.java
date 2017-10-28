package net.shtyftu.ubiquode.model.persist.composite.event.quest;

import net.shtyftu.ubiquode.model.projection.Quest;

/**
 * @author shtyftu
 */
public class QuestEnableEvent extends QuestEvent {

    public QuestEnableEvent(String questId) {
        super(questId);
    }

    @Override
    public void applyTo(Quest entity) {
        entity.setEnabled(true);
        entity.setDeadlineAt(now() + entity.getProto().getDeadline());
    }
}
