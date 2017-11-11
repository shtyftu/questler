package net.shtyftu.ubiquode.dao.plain;

import com.google.gson.reflect.TypeToken;
import java.util.concurrent.TimeUnit;
import javax.annotation.PostConstruct;
import net.shtyftu.ubiquode.model.persist.simple.QuestProto;
import org.springframework.stereotype.Component;

/**
 * @author shtyftu
 */
@Component
public class QuestProtoDao extends RedisModelWithIdDao<QuestProto> {

    public QuestProtoDao() {
        super(new TypeToken<QuestProto>() {
        });
    }
}
