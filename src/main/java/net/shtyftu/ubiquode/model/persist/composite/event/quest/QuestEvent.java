package net.shtyftu.ubiquode.model.persist.composite.event.quest;

import net.shtyftu.ubiquode.model.persist.composite.event.AEvent;
import net.shtyftu.ubiquode.model.projection.Quest;

/**
 * @author shtyftu
 */
public abstract class QuestEvent extends AEvent<Quest> {

    QuestEvent(String questId) {
        super(questId);
    }

    public abstract String getViewName();
}
