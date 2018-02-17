package net.shtyftu.ubiquode.model.persist.composite.event.user;

import net.shtyftu.ubiquode.model.persist.composite.event.AEvent;
import net.shtyftu.ubiquode.model.projection.User;

/**
 * @author shtyftu
 */
public abstract class UserEvent extends AEvent<User> {

    UserEvent(String id, long otherEventTime) {
        super(id, otherEventTime);
    }

}
