package net.shtyftu.ubiquode.processor;

import net.shtyftu.ubiquode.dao.list.event.EventDao;
import net.shtyftu.ubiquode.model.persist.composite.event.AEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Nullable;

/**
 * @author shtyftu
 */
public abstract class Processor<T, E extends AEvent<T>> {

    private final Logger log = LoggerFactory.getLogger(this.getClass());

    private final EventDao<E> eventDao;

    Processor(EventDao<E> eventDao) {
        this.eventDao = eventDao;
    }

    @Nullable
    public T getById(String id) {
        final T result = createNew(id);
        try {
            eventDao.processAll(id, event -> {
                try {
                    logEvent(id, event);
                    event.applyTo(result);
                } catch (Exception e) {
                    log.error("Unable to apply event: {}", event, e);
                    throw e;
                }
            });
        } catch (Exception e) {
            return null;
        }
        return result;
    }

    private void logEvent(String id, E event) {
        log.debug("Modifying entity: {} with event: {}", id, event);
    }

    protected void save(E entity) {
        eventDao.add(entity.getId(), entity);
    }

    protected abstract T createNew(String key);

}
