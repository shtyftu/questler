package net.shtyftu.ubiquode.dao.list;

import com.google.gson.reflect.TypeToken;
import com.lambdaworks.redis.api.sync.RedisCommands;
import com.lambdaworks.redis.output.ValueStreamingChannel;
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
        final ValueStreamingChannel<String> valueStreamingChannel = value -> channel.onValue(deserialize(value));
        final String redisKey = getRedisKey(id);
        final RedisCommands<String, String> redisCommands = redisCmd();
        return redisCommands.lrange(valueStreamingChannel, redisKey, 0, -1);
    }

    @Override
    public Long add(String id, E e) {
        return redisCmd().rpush(getRedisKey(e.getId()), serialize(e));
    }

    private String getRedisKey(String id) {
        return "event:" + redisTypeName + ":" + id;
    }

}
