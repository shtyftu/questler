package net.shtyftu.ubiquode.processor;

import net.shtyftu.ubiquode.dao.list.event.QuestEventDao;
import net.shtyftu.ubiquode.dao.plain.QuestProtoDao;
import net.shtyftu.ubiquode.model.persist.composite.event.quest.QuestCompleteEvent;
import net.shtyftu.ubiquode.model.persist.composite.event.quest.QuestEnableEvent;
import net.shtyftu.ubiquode.model.persist.composite.event.quest.QuestEvent;
import net.shtyftu.ubiquode.model.persist.composite.event.quest.QuestLockEvent;
import net.shtyftu.ubiquode.model.persist.composite.event.quest.QuestSetProtoIdEvent;
import net.shtyftu.ubiquode.model.persist.composite.event.quest.QuestTriggerEvent;
import net.shtyftu.ubiquode.model.projection.Quest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author shtyftu
 */
@Component
public class QuestRepository extends ARepository<Quest, QuestEvent> {

    private final QuestProtoDao protoDao;

    @Autowired
    public QuestRepository(QuestEventDao eventDao, QuestProtoDao protoDao) {
        super(eventDao);
        this.protoDao = protoDao;
    }

    @Override
    protected Quest createNew(String questId) {
        return new Quest(questId, protoDao);
    }

    public long lock(String questId, String userId) {
        final QuestLockEvent event = new QuestLockEvent(questId, userId);
        save(event);
        return event.getTime();
    }

    public long complete(String questId) {
        final QuestCompleteEvent event = new QuestCompleteEvent(questId);
        save(event);
        return event.getTime();
    }

    public void enable(String questId, long deadline) {
        save(new QuestEnableEvent(questId, deadline));
    }

    public void trigger(String questId, long deadline) {
        save(new QuestTriggerEvent(questId, deadline));
    }

    public void setProtoId(String questId, String protoId) {
        save(new QuestSetProtoIdEvent(questId, protoId));
    }
}
