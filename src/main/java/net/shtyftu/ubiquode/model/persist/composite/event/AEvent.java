package net.shtyftu.ubiquode.model.persist.composite.event;

import net.shtyftu.ubiquode.model.AModel;

/**
 * @author shtyftu
 */
public abstract class AEvent<T> extends AModel implements Event<T> {

    private final long time;
    private final String id;

    public AEvent(String id) {
        this.id = id;
        this.time = System.currentTimeMillis();
    }

    @Override
    public String getKey() {
        return id;
    }

    @Override
    public long getTime() {
        return time;
    }

}
