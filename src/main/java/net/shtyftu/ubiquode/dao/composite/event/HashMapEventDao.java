package net.shtyftu.ubiquode.dao.composite.event;

import net.shtyftu.ubiquode.dao.composite.HashMapCompositeKeyDao;
import net.shtyftu.ubiquode.model.persist.composite.event.Event;

/**
 * @author shtyftu
 */
public abstract class HashMapEventDao<E extends Event<?>> extends HashMapCompositeKeyDao<E, Long>
        implements EventDao<E> {

}
