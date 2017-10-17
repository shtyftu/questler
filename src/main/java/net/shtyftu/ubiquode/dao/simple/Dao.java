package net.shtyftu.ubiquode.dao.simple;

import java.util.Set;
import net.shtyftu.ubiquode.model.persist.simple.PersistEntity;

/**
 * @author shtyftu
 */
public interface Dao<E extends PersistEntity<K>, K> {

    E getByKey(K key);

    Set<K> getAllKeys();

    void save(E entity);
}
