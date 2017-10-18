package net.shtyftu.ubiquode.dao.simple;

import java.util.concurrent.TimeUnit;
import net.shtyftu.ubiquode.model.persist.simple.QuestProto;
import org.springframework.stereotype.Component;

/**
 * @author shtyftu
 */
@Component
public class QuestProtoDao extends HashMapDao<QuestProto, String> implements Dao<QuestProto, String> {

    public QuestProtoDao() {
        save(new QuestProto("id1", "clean table", TimeUnit.HOURS.toMillis(6), TimeUnit.HOURS.toMillis(24)));
        save(new QuestProto("id2", "move clothes", TimeUnit.HOURS.toMillis(12),TimeUnit.HOURS.toMillis(48)));
    }
}
