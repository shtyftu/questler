package net.shtyftu.ubiquode.dao.composite.event;

import net.shtyftu.ubiquode.dao.composite.CompositeIdDao;
import net.shtyftu.ubiquode.model.persist.composite.event.AEvent;

/**
 * @author shtyftu
 */
public interface EventDao<E extends AEvent<?>> extends CompositeIdDao<E, Long> {

}
