package net.shtyftu.ubiquode.processor;

import java.util.Collections;
import java.util.List;
import net.shtyftu.ubiquode.dao.composite.event.EventDao;
import net.shtyftu.ubiquode.model.persist.composite.event.Event;

/**
 * @author shtyftu
 */
public abstract class Processor<T, K, E extends Event<T, K>> {

    private final EventDao<E, K> eventDao;

    public Processor(EventDao<E, K> eventDao) {
        this.eventDao = eventDao;
    }

    public T getByKey(K key) {
        final T result = createNew(key);

        final List<E> events = eventDao.getByKey(key);
        Collections.sort(events);
        events.forEach(e-> e.applyTo(result));
        return result;

    }

    protected abstract T createNew(K key);

}
