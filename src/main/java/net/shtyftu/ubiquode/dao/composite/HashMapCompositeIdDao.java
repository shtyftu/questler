package net.shtyftu.ubiquode.dao.composite;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import net.shtyftu.ubiquode.model.persist.composite.ModelWithCompositeId;

/**
 * @author shtyftu
 */
public class HashMapCompositeIdDao<E extends ModelWithCompositeId<C>, C> implements CompositeIdDao<E, C> {

    private final Map<String, Map<C, E>> data = new HashMap<>();

    @Override
    public List<E> getById(String id) {
        final Map<C, E> result = data.get(id);
        return result == null ? new ArrayList<>() : new ArrayList<>(result.values());
    }

    @Override
    public E getById(String id, C clusteringId) {
        final Map<C, E> clustersData = data.get(id);
        return clustersData == null ? null : clustersData.get(clusteringId);
    }

    @Override
    public void save(E entity) {
        final String id = entity.getId();
        final Map<C, E> clustersData = data.computeIfAbsent(id, k -> new HashMap<>());
        clustersData.put(entity.getClusteringId(), entity);
    }
}
