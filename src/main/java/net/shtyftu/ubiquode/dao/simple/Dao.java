package net.shtyftu.ubiquode.dao.simple;

import java.util.List;
import java.util.Set;
import net.shtyftu.ubiquode.model.persist.simple.PersistEntity;

/**
 * @author shtyftu
 */
public interface Dao<E extends PersistEntity> {

    E getById(String key);

    Set<String> getAllIds();

    List<E> getAll();

    void save(E entity);

    void save(List<E> questProtoList);
}
