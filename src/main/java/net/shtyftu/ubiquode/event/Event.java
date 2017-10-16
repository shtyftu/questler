package net.shtyftu.ubiquode.event;

/**
 * @author shtyftu
 */
public interface Event<T> extends Comparable<T> {

    T applyTo(T type);

}
