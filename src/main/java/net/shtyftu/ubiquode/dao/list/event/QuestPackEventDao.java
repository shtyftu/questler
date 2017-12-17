package net.shtyftu.ubiquode.dao.list.event;

import com.google.gson.reflect.TypeToken;
import net.shtyftu.ubiquode.dao.RedisClientService;
import net.shtyftu.ubiquode.model.persist.composite.event.questPack.QuestPackEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author shtyftu
 */
@Component
public class QuestPackEventDao extends RedisEventDao<QuestPackEvent> {

    @Autowired
    public QuestPackEventDao(RedisClientService clientService) {
        super(
                new TypeToken<QuestPackEvent>() {
                },
                clientService
        );
    }
}
