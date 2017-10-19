package net.shtyftu.ubiquode.dao.composite;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import net.shtyftu.ubiquode.model.persist.composite.CompositeKeyPersistEntity;

/**
 * @author shtyftu
 */
public class HashMapCompositeKeyDao<E extends CompositeKeyPersistEntity<P>, P> implements CompositeKeyDao<E, P> {

    private final Map<String, Map<P, E>> data = new HashMap<>();

    @Override
    public List<E> getByKey(String key) {
        final Map<P, E> result = data.get(key);
        return result == null ? new ArrayList<>() : new ArrayList<>(result.values());
    }

    @Override
    public E getByKey(String key, P partitionKey) {
        final Map<P, E> allByKey = data.get(key);
        return allByKey == null ? null : allByKey.get(partitionKey);
    }

    @Override
    public void save(E entity) {
        final String key = entity.getKey();
        final Map<P, E> allByKey = data.computeIfAbsent(key, k -> new HashMap<>());
        allByKey.put(entity.getPartitionKey(), entity);
    }
}
