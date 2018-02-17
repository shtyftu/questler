package net.shtyftu.ubiquode.model.persist.composite.event;

import net.shtyftu.ubiquode.model.persist.composite.ModelWithCompositeId;

/**
 * @author shtyftu
 */
public abstract class AEvent<T> extends ModelWithCompositeId<Long> implements Comparable<AEvent<T>>  {

    protected AEvent(String id) {
        super(id, System.currentTimeMillis());
    }

    public abstract void applyTo(T entity);

    @Override
    public int compareTo(AEvent<T> o) {
        return o == null ? 1 : Long.compare(getTime(), o.getTime());
    }

    public long getTime() {
        return getClusteringId();
    }
}
