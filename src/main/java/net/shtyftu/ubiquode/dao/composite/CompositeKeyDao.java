package net.shtyftu.ubiquode.dao.composite;

import java.util.List;
import net.shtyftu.ubiquode.model.persist.composite.CompositeKeyPersistEntity;

/**
 * @author shtyftu
 */
public interface CompositeKeyDao<E extends CompositeKeyPersistEntity<K, P>, K, P>  {

    List<E> getByKey(K key);

    E getByKey(K key, P partitionKey);

    void save(E entity);

}
