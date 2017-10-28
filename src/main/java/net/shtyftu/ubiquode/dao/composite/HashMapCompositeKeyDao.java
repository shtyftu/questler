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
    public List<E> getById(String id) {
        final Map<P, E> result = data.get(id);
        return result == null ? new ArrayList<>() : new ArrayList<>(result.values());
    }

    @Override
    public E getById(String id, P partitionId) {
        final Map<P, E> allByKey = data.get(id);
        return allByKey == null ? null : allByKey.get(partitionId);
    }

    @Override
    public void save(E entity) {
        final String key = entity.getId();
        final Map<P, E> allByKey = data.computeIfAbsent(key, k -> new HashMap<>());
        allByKey.put(entity.getPartitionKey(), entity);
    }
}
