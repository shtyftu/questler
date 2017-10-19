package net.shtyftu.ubiquode.dao.simple;

import java.util.Set;
import net.shtyftu.ubiquode.model.persist.simple.PersistEntity;

/**
 * @author shtyftu
 */
public interface Dao<E extends PersistEntity> {

    E getByKey(String key);

    Set<String> getAllKeys();

    void save(E entity);
}
