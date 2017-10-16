package net.shtyftu.ubiquode.event.processor;

import java.util.List;
import net.shtyftu.ubiquode.dao.Dao;
import net.shtyftu.ubiquode.dao.QuestEventDao;
import net.shtyftu.ubiquode.dao.QuestProtoDao;
import net.shtyftu.ubiquode.event.QuestEvent;
import net.shtyftu.ubiquode.model.QuestProto;
import net.shtyftu.ubiquode.model.QuestState;
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
        final QuestProto result = protoDao.getByKey(key);
        return null;
    }
}
