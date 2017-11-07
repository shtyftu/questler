package net.shtyftu.ubiquode.dao.list.event;

import com.google.gson.reflect.TypeToken;
import net.shtyftu.ubiquode.model.persist.composite.event.user.UserEvent;
import org.springframework.stereotype.Component;

/**
 * @author shtyftu
 */
@Component
public class UserEventDao extends RedisEventDao<UserEvent> {

    public UserEventDao() {
        super(new TypeToken<UserEvent>(){});
    }
}
