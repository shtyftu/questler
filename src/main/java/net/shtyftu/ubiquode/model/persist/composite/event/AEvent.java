package net.shtyftu.ubiquode.model.persist.composite.event;

/**
 * @author shtyftu
 */
public abstract class AEvent<T> implements Event<T, String> {

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
