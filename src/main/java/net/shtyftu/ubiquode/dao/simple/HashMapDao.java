package net.shtyftu.ubiquode.dao.simple;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import net.shtyftu.ubiquode.model.persist.simple.PersistEntity;

/**
 * @author shtyftu
 */
public class HashMapDao<E extends PersistEntity> implements Dao<E> {

    private final Map<String, E> data = new HashMap<>();

    @Override
    public E getByKey(String key) {
        return data.get(key);
    }

    @Override
    public Set<String> getAllKeys() {
        return data.keySet();
    }

    @Override
    public void save(E entity) {
        data.put(entity.getKey(), entity);
    }
}
