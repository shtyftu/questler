package net.shtyftu.ubiquode.dao.list.event;

import net.shtyftu.ubiquode.dao.list.ListDao;
import net.shtyftu.ubiquode.model.persist.composite.event.AEvent;

/**
 * @author shtyftu
 */
public interface EventDao<E extends AEvent<?>> extends ListDao<E> {

}
