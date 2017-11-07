package net.shtyftu.ubiquode.dao.list.event;

import com.google.gson.reflect.TypeToken;
import net.shtyftu.ubiquode.model.persist.composite.event.quest.QuestEvent;
import org.springframework.stereotype.Component;

/**
 * @author shtyftu
 */
@Component
public class QuestEventDao extends RedisEventDao<QuestEvent> {

    public QuestEventDao() {
        super(new TypeToken<QuestEvent>() {
        });
    }
}
