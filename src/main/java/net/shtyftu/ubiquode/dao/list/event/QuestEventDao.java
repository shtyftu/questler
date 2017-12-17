package net.shtyftu.ubiquode.dao.list.event;

import com.google.gson.reflect.TypeToken;
import net.shtyftu.ubiquode.dao.RedisClientService;
import net.shtyftu.ubiquode.model.persist.composite.event.quest.QuestEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author shtyftu
 */
@Component
public class QuestEventDao extends RedisEventDao<QuestEvent> {

    @Autowired
    public QuestEventDao(RedisClientService clientService) {
        super(
                new TypeToken<QuestEvent>() {
                },
                clientService
        );
    }
}
