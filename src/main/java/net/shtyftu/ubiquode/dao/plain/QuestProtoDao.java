package net.shtyftu.ubiquode.dao.plain;

import com.google.gson.reflect.TypeToken;
import net.shtyftu.ubiquode.dao.RedisClientService;
import net.shtyftu.ubiquode.model.persist.simple.QuestProto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author shtyftu
 */
@Component
public class QuestProtoDao extends RedisModelWithIdDao<QuestProto> {

    @Autowired
    public QuestProtoDao(RedisClientService clientService) {
        super(
                new TypeToken<QuestProto>() {
                },
                clientService)
        ;
    }
}
