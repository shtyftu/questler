package net.shtyftu.ubiquode.dao.plain;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import net.shtyftu.ubiquode.model.persist.simple.ModelWithId;

/**
 * @author shtyftu
 */
public interface Dao<E extends ModelWithId> {

    @Nullable
    E getById(String id);

    @Nonnull
    Optional<E> getOptional(String id);

    Set<String> getAllIds();

    List<E> getAll();

    void save(@Nonnull E entity);

    void save(List<E> entities);
}
