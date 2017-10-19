package net.shtyftu.ubiquode.dao.composite;

import java.util.List;
import net.shtyftu.ubiquode.model.persist.composite.CompositeKeyPersistEntity;

/**
 * @author shtyftu
 */
public interface CompositeKeyDao<E extends CompositeKeyPersistEntity<P>, P>  {

    List<E> getByKey(String key);

    E getByKey(String key, P partitionKey);

    void save(E entity);

}
