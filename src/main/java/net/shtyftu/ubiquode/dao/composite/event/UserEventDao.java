package net.shtyftu.ubiquode.dao.composite.event;

import net.shtyftu.ubiquode.model.persist.composite.event.user.UserEvent;
import org.springframework.stereotype.Component;

/**
 * @author shtyftu
 */
@Component
public class UserEventDao extends HashMapEventDao<UserEvent, String>  {

}
