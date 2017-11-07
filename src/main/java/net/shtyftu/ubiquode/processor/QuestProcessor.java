package net.shtyftu.ubiquode.processor;

import net.shtyftu.ubiquode.dao.list.event.QuestEventDao;
import net.shtyftu.ubiquode.dao.plain.QuestProtoDao;
import net.shtyftu.ubiquode.model.persist.composite.event.quest.QuestCompleteEvent;
import net.shtyftu.ubiquode.model.persist.composite.event.quest.QuestEnableEvent;
import net.shtyftu.ubiquode.model.persist.composite.event.quest.QuestEvent;
import net.shtyftu.ubiquode.model.persist.composite.event.quest.QuestLockEvent;
import net.shtyftu.ubiquode.model.persist.composite.event.quest.QuestSetProtoIdEvent;
import net.shtyftu.ubiquode.model.persist.simple.QuestProto;
import net.shtyftu.ubiquode.model.projection.Quest;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author shtyftu
 */
@Component
public class QuestProcessor extends Processor<Quest, QuestEvent> {

    private final QuestProtoDao protoDao;

    @Autowired
    public QuestProcessor(QuestEventDao eventDao, QuestProtoDao protoDao) {
        super(eventDao);
        this.protoDao = protoDao;
    }

    @Override
    protected Quest createNew(String questId) {
        return new Quest(questId);
    }

    @Override
    public Quest getById(String id) {
        final Quest quest = super.getById(id);
        if (quest.getProto() == null) {
            final String protoId = quest.getProtoId();
            if (StringUtils.isBlank(protoId)) {
                throw new IllegalStateException("there is no protoId for quest [" + id + "]");
            }
            final QuestProto proto = protoDao.getById(protoId);
            if (proto == null) {
                throw new IllegalStateException(
                        "there is no proto for protoId [" + protoId + "] for questId [" + id +"]");
            }
            quest.setProto(proto);
        }
        return quest;
    }

    public void lock(String questId, String userId) {
        save(new QuestLockEvent(questId, userId));
    }

    public void complete(String questId) {
        save(new QuestCompleteEvent(questId));
    }

    public void enable(String questId, long deadline) {
        save(new QuestEnableEvent(questId, deadline));
    }

    public void setProtoId(String questId, String protoId) {
        save(new QuestSetProtoIdEvent(questId, protoId));
    }
}
