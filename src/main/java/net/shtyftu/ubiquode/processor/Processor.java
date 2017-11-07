package net.shtyftu.ubiquode.processor;

import net.shtyftu.ubiquode.dao.list.event.EventDao;
import net.shtyftu.ubiquode.model.persist.composite.event.AEvent;

/**
 * @author shtyftu
 */
public abstract class Processor<T, E extends AEvent<T>> {

    private final EventDao<E> eventDao;

    Processor(EventDao<E> eventDao) {
        this.eventDao = eventDao;
    }

    public T getById(String id) {
        final T result = createNew(id);
        eventDao.processAll(id, event -> event.applyTo(result));
        return result;
    }

    protected void save(E entity) {
        eventDao.add(entity.getId(), entity);
    }

    protected abstract T createNew(String key);

}
