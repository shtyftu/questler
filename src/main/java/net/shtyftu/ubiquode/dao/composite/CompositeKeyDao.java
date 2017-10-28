package net.shtyftu.ubiquode.dao.composite;

import java.util.List;
import net.shtyftu.ubiquode.model.persist.composite.CompositeKeyPersistEntity;

/**
 * @author shtyftu
 */
public interface CompositeKeyDao<E extends CompositeKeyPersistEntity<P>, P>  {

    List<E> getById(String id);

    E getById(String id, P partitionId);

    void save(E entity);

}
