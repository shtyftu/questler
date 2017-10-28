package net.shtyftu.ubiquode.dao.simple;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import net.shtyftu.ubiquode.model.persist.simple.PersistEntity;

/**
 * @author shtyftu
 */
public abstract class HashMapDao<E extends PersistEntity> implements Dao<E> {

    private final Map<String, E> data = new HashMap<>();

    @Override
    public E getById(String key) {
        return data.get(key);
    }

    @Override
    public Set<String> getAllIds() {
        return data.keySet();
    }

    @Override
    public void save(E entity) {
        data.put(entity.getId(), entity);
    }

    @Override
    public List<E> getAll() {
        return getAllIds().stream()
                .map(this::getById)
                .collect(Collectors.toList());
    }

    @Override
    public void save(List<E> entityList) {
        entityList.forEach(this::save);
    }
}
