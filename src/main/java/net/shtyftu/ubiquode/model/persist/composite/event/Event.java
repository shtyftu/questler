package net.shtyftu.ubiquode.model.persist.composite.event;

import net.shtyftu.ubiquode.model.persist.composite.CompositeKeyPersistEntity;

/**
 * @author shtyftu
 */
public interface Event<T, K> extends Comparable<Event<T, K>>, CompositeKeyPersistEntity<K, Long> {

    long getTime();

    void applyTo(T entity);

    @Override
    default Long getPartitionKey() {
        return getTime();
    }

    @Override
    default int compareTo(Event<T, K> o) {
        return o == null ? 1 : Long.compare(getTime(), o.getTime());
    }

}
