package net.shtyftu.ubiquode.model.persist.composite.event.quest;

import net.shtyftu.ubiquode.model.projection.QuestState;

/**
 * @author shtyftu
 */
public class QuestEnableEvent extends QuestEvent {

    public QuestEnableEvent(String questId) {
        super(questId);
    }

    @Override
    public void applyTo(QuestState entity) {
        entity.setEnabled(true);
        entity.setDeadlineAt(now() + entity.getProto().getDeadline());
    }
}
