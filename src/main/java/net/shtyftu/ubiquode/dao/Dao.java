package net.shtyftu.ubiquode.dao;

/**
 * @author shtyftu
 */
public interface Dao<E, K> {

    E getByKey(K key);
}
