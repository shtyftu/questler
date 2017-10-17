package net.shtyftu.ubiquode.dao.composite;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import net.shtyftu.ubiquode.model.persist.composite.CompositeKeyPersistEntity;

/**
 * @author shtyftu
 */
public class HashMapCompositeKeyDao<E extends CompositeKeyPersistEntity<K, P>, K, P>
        implements CompositeKeyDao<E, K, P> {

    private final Map<K, Map<P, E>> data = new HashMap<>();

    @Override
    public List<E> getByKey(K key) {
        final Map<P, E> result = data.get(key);
        return result == null ? new ArrayList<>() : new ArrayList<>(result.values());
    }

    @Override
    public E getByKey(K key, P partitionKey) {
        final Map<P, E> allByKey = data.get(key);
        return allByKey == null ? null : allByKey.get(partitionKey);
    }

    @Override
    public void save(E entity) {
        final K key = entity.getKey();
        final Map<P, E> allByKey = data.computeIfAbsent(key, k -> new HashMap<>());
        allByKey.put(entity.getPartitionKey(), entity);
    }
}
