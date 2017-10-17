package net.shtyftu.ubiquode.processor;

import net.shtyftu.ubiquode.dao.composite.event.QuestEventDao;
import net.shtyftu.ubiquode.dao.simple.QuestProtoDao;
import net.shtyftu.ubiquode.model.persist.composite.event.quest.QuestEvent;
import net.shtyftu.ubiquode.model.persist.simple.QuestProto;
import net.shtyftu.ubiquode.model.projection.QuestState;
import net.shtyftu.ubiquode.model.projection.QuestState.State;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author shtyftu
 */
public class QuestStateProcessor extends Processor<QuestState, String, QuestEvent> {

    private final QuestProtoDao protoDao;

    @Autowired
    public QuestStateProcessor(QuestEventDao eventDao, QuestProtoDao protoDao) {
        super(eventDao);
        this.protoDao = protoDao;
    }

    @Override
    protected QuestState createNew(String key) {
        final QuestProto proto = protoDao.getByKey(key);
        return new QuestState(proto, null, null, State.Disabled);
    }
}
