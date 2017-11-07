package net.shtyftu.ubiquode.dao.list.event;

import com.google.gson.reflect.TypeToken;
import java.lang.reflect.Type;
import net.shtyftu.ubiquode.model.persist.composite.event.questPack.QuestPackEvent;
import org.springframework.stereotype.Component;

/**
 * @author shtyftu
 */
@Component
public class QuestPackEventDao extends RedisEventDao<QuestPackEvent> {

    public QuestPackEventDao() {
        super(new TypeToken<QuestPackEvent>() {
        });
    }
}
