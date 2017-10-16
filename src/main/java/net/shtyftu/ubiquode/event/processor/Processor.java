package net.shtyftu.ubiquode.event.processor;

import java.util.List;
import net.shtyftu.ubiquode.dao.Dao;
import net.shtyftu.ubiquode.event.Event;

/**
 * @author shtyftu
 */
public abstract class Processor<T, K, E extends Event<T>> {

    private final Dao<List<E>, K> eventDao;

    public Processor(Dao<List<E>, K> eventDao) {
        this.eventDao = eventDao;
    }

    public T getByKey(K key) {
        final T result = createNew(key);

        final List<E> events = eventDao.getByKey(key);
        events.sort(null);
        events.forEach(e-> e.applyTo(result));
        return result;

    }

    protected abstract T createNew(K key);

}
