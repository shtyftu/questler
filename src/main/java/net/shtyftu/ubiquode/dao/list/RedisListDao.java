package net.shtyftu.ubiquode.dao.list;

import com.google.gson.reflect.TypeToken;
import com.lambdaworks.redis.api.sync.RedisCommands;
import com.lambdaworks.redis.output.ValueStreamingChannel;
import java.util.ArrayList;
import java.util.List;
import net.shtyftu.ubiquode.dao.RedisClientService;
import net.shtyftu.ubiquode.dao.RedisDao;
import net.shtyftu.ubiquode.model.persist.simple.ModelWithId;

/**
 * @author shtyftu
 */
public abstract class RedisListDao<E extends ModelWithId> extends RedisDao<E> implements ListDao<E> {

    public RedisListDao(TypeToken<E> typeToken, RedisClientService clientService) {
        super(typeToken, clientService);
    }

    @Override
    public Long processAll(String id, ValueStreamingChannel<E> channel) {
        return processLRange(id, 0, -1, channel);
    }

    @Override
    public List<E> getAll(String id) {
        return getAllInternal(id, false);
    }

    @Override
    public Long add(String id, E e) {
        return redisCmd().rpush(getRedisKey(e.getId()), serialize(e));
    }

    private List<E> getAllInternal(String id, boolean descending) {
        final ArrayList<E> result = new ArrayList<>();
        processLRange(id, 0, -1, value -> {
            if (descending) {
                result.add(0, value);
            } else {
                result.add(value);
            }
        });
        return result;
    }

    private Long processLRange(String id, long start, long stop, ValueStreamingChannel<E> channel) {
        final ValueStreamingChannel<String> valueStreamingChannel = value -> channel.onValue(deserialize(value));
        final String redisKey = getRedisKey(id);
        final RedisCommands<String, String> redisCommands = redisCmd();
        return redisCommands.lrange(valueStreamingChannel, redisKey, start, stop);
    }

    private String getRedisKey(String id) {
        return "event:" + redisTypeName + ":" + id;
    }

}
