package net.shtyftu.ubiquode.dao.plain;

import com.google.gson.reflect.TypeToken;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;
import net.shtyftu.ubiquode.dao.RedisDao;
import net.shtyftu.ubiquode.model.persist.simple.ModelWithId;

/**
 * @author shtyftu
 */
public class RedisModelWithIdDao<E extends ModelWithId> extends RedisDao<E> implements Dao<E> {

    public RedisModelWithIdDao(TypeToken<E> typeToken) {
        super(typeToken);
    }

    private String getKeysKey() {
        return "keys:" + redisTypeName;
    }

    private String getModelKey(String id) {
        return "model:" + redisTypeName + ":" + id;
    }

    @Override
    public E getById(String id) {
        final String model = redisCmd().get(getModelKey(id));
        return deserialize(model);
    }

    @Override
    public Set<String> getAllIds() {
        return redisCmd().smembers(getKeysKey());
    }

    @Override
    public List<E> getAll() {
        final String keysKey = getKeysKey();
        final Set<String> keySet = redisCmd().smembers(keysKey);
        return keySet.stream().map(this::getById).filter(Objects::nonNull).collect(Collectors.toList());
    }

    @Override
    public void save(E entity) {
        final String id = entity.getId();
        redisCmd().set(getModelKey(id), serialize(entity));
        redisCmd().sadd(getKeysKey(), id);
    }

    @Override
    public void save(List<E> entities) {
        for (E e : entities) {
            save(e);
        }
    }

}