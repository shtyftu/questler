package net.shtyftu.ubiquode.dao.composite.event;

import net.shtyftu.ubiquode.dao.composite.HashMapCompositeIdDao;
import net.shtyftu.ubiquode.model.persist.composite.event.AEvent;

/**
 * @author shtyftu
 */
public abstract class HashMapEventDao<E extends AEvent<?>> extends HashMapCompositeIdDao<E, Long>
        implements EventDao<E> {

}
