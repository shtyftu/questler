package net.shtyftu.ubiquode.processor;

import java.util.Collections;
import java.util.List;
import net.shtyftu.ubiquode.dao.composite.event.EventDao;
import net.shtyftu.ubiquode.model.persist.composite.event.AEvent;

/**
 * @author shtyftu
 */
public abstract class Processor<T, E extends AEvent<T>> {

    private final EventDao<E> eventDao;

    public Processor(EventDao<E> eventDao) {
        this.eventDao = eventDao;
    }

    public T getById(String id) {
        final T result = createNew(id);

        final List<E> events = eventDao.getById(id);
        Collections.sort(events);
        events.forEach(e-> e.applyTo(result));
        return result;
    }

    protected void save(E entity) {
        eventDao.save(entity);
    }

    protected abstract T createNew(String key);

}
