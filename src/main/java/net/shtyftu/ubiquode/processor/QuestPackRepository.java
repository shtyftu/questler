package net.shtyftu.ubiquode.processor;

import net.shtyftu.ubiquode.dao.list.event.QuestPackEventDao;
import net.shtyftu.ubiquode.dao.plain.QuestProtoDao;
import net.shtyftu.ubiquode.model.QuestPack;
import net.shtyftu.ubiquode.model.persist.composite.event.questPack.QuestPackAddQuestEvent;
import net.shtyftu.ubiquode.model.persist.composite.event.questPack.QuestPackAddScoresToPlayerEvent;
import net.shtyftu.ubiquode.model.persist.composite.event.questPack.QuestPackAddUserEvent;
import net.shtyftu.ubiquode.model.persist.composite.event.questPack.QuestPackEvent;
import net.shtyftu.ubiquode.model.persist.composite.event.questPack.QuestPackRenameEvent;
import net.shtyftu.ubiquode.model.persist.simple.QuestProto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author shtyftu
 */
@Component
public class QuestPackRepository extends ARepository<QuestPack, QuestPackEvent> {

    private final QuestProtoDao questProtoDao;

    @Autowired
    public QuestPackRepository(QuestPackEventDao eventDao, QuestProtoDao questProtoDao) {
        super(eventDao);
        this.questProtoDao = questProtoDao;
    }

    @Override
    protected QuestPack createNew(String key) {
        return new QuestPack(key);
    }

    public String addQuest(String packId, String protoId, String userId) {
        final QuestProto proto = questProtoDao.getById(protoId);
        if (proto == null) {
            return null;
        }
        final QuestPackAddQuestEvent event = new QuestPackAddQuestEvent(packId, userId, protoId);
        save(event);
        return event.getQuestId();
    }

    public void setName(String packId, String name) {
        save(new QuestPackRenameEvent(packId, name));
    }

    public void addScores(String packId, String userId, int scores, long eventTime) {
        save(new QuestPackAddScoresToPlayerEvent(packId, userId, scores, eventTime));
    }

    public long addUser(String packId, String userId, String inviterId) {
        final QuestPackAddUserEvent event = new QuestPackAddUserEvent(packId, userId, inviterId);
        save(event);
        return event.getTime();
    }
}
