package net.shtyftu.ubiquode.model.persist.composite.event.quest;

import net.shtyftu.ubiquode.model.persist.composite.event.AEvent;
import net.shtyftu.ubiquode.model.persist.composite.event.Event;
import net.shtyftu.ubiquode.model.projection.QuestState;

/**
 * @author shtyftu
 */
public abstract class QuestEvent extends AEvent<QuestState> {

    public QuestEvent(String questId) {
        super(questId);
    }

}
