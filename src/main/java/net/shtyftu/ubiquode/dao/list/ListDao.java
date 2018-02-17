package net.shtyftu.ubiquode.dao.list;

import com.lambdaworks.redis.output.ValueStreamingChannel;
import java.util.List;
import net.shtyftu.ubiquode.model.persist.simple.ModelWithId;

/**
 * @author shtyftu
 */
public interface ListDao<E extends ModelWithId> {

    Long processAll(String id, ValueStreamingChannel<E> channel);

    List<E> getAll(String id);

    Long add(String id, E e);

}
