package net.shtyftu.ubiquode.model.persist.composite.event.questPack;

import net.shtyftu.ubiquode.model.QuestPack;
import net.shtyftu.ubiquode.model.persist.composite.event.AEvent;

/**
 * @author shtyftu
 */
public abstract class QuestPackEvent extends AEvent<QuestPack> {

    QuestPackEvent(String questPackId) {
        super(questPackId);
    }

    QuestPackEvent(String id, long otherEventTime) {
        super(id, otherEventTime);
    }
}
