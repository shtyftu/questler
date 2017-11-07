package net.shtyftu.ubiquode.dao.list;

import com.lambdaworks.redis.output.ValueStreamingChannel;
import net.shtyftu.ubiquode.model.persist.simple.ModelWithId;

/**
 * @author shtyftu
 */
public interface ListDao<E extends ModelWithId> {

    Long processAll(String id, ValueStreamingChannel<E> channel);

    Long add(String id, E e);

}
