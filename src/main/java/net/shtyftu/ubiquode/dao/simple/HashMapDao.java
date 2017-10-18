package net.shtyftu.ubiquode.dao.simple;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import net.shtyftu.ubiquode.model.persist.simple.PersistEntity;

/**
 * @author shtyftu
 */
public class HashMapDao<E extends PersistEntity<K>, K> implements Dao<E, K> {

    private final Map<K, E> data = new HashMap<>();

    @Override
    public E getByKey(K key) {
        return data.get(key);
    }

    @Override
    public Set<K> getAllKeys() {
        return data.keySet();
    }

    @Override
    public void save(E entity) {
        data.put(entity.getKey(), entity);
    }
}
