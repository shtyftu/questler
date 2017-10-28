package net.shtyftu.ubiquode.dao.composite;

import java.util.List;
import net.shtyftu.ubiquode.model.persist.composite.ModelWithCompositeId;

/**
 * @author shtyftu
 */
public interface CompositeIdDao<E extends ModelWithCompositeId<C>, C>  {

    List<E> getById(String id);

    E getById(String id, C clusteringId);

    void save(E entity);

}
