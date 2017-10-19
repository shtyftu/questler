package net.shtyftu.ubiquode.dao.composite.event;

import net.shtyftu.ubiquode.dao.composite.CompositeKeyDao;
import net.shtyftu.ubiquode.model.persist.composite.event.Event;

/**
 * @author shtyftu
 */
public interface EventDao<E extends Event<?>> extends CompositeKeyDao<E, Long> {

}
