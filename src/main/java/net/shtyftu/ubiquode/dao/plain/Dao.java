package net.shtyftu.ubiquode.dao.plain;

import java.util.List;
import java.util.Set;
import net.shtyftu.ubiquode.model.persist.simple.ModelWithId;

/**
 * @author shtyftu
 */
public interface Dao<E extends ModelWithId> {

    E getById(String id);

    Set<String> getAllIds();

    List<E> getAll();

    void save(E entity);

    void save(List<E> entities);
}
