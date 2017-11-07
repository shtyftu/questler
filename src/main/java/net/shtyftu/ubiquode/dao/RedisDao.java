package net.shtyftu.ubiquode.dao;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.lambdaworks.redis.RedisClient;
import com.lambdaworks.redis.api.StatefulRedisConnection;
import com.lambdaworks.redis.api.sync.RedisCommands;
import java.lang.reflect.Type;
import java.util.Arrays;
import java.util.stream.Collectors;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author shtyftu
 */
public abstract class RedisDao<E> {

    private static final String REDIS_HOST = "redis://localhost";
    private static final Gson GSON = new Gson();
    private static final StatefulRedisConnection<String, String> connection = RedisClient.create(REDIS_HOST).connect();

    private final Logger log = LoggerFactory.getLogger(this.getClass());

    protected final Type type;
    protected final String redisTypeName;

    public RedisDao(TypeToken<E> typeToken) {
        this.type = typeToken.getType();

        final String typeString = type.toString();
        final String className = (typeString.substring(typeString.lastIndexOf(".") + 1));
        redisTypeName = Arrays.stream(className.split("(?=\\p{Upper})"))
                .map(String::toLowerCase)
                .collect(Collectors.joining("-"));
    }

    protected RedisCommands<String, String> redisCmd() {
        return connection.sync();
    }

    protected String serialize(E e) {
        final String className = e.getClass().getName();
        final SerializedEntity entity = new SerializedEntity(className, GSON.toJson(e));
        return GSON.toJson(entity);
    }

    protected E deserialize(String string) {
        if (StringUtils.isNotBlank(string)) {
            try {
                final SerializedEntity serialized = GSON.fromJson(string, SerializedEntity.class);
                final Class<?> clazz = Class.forName(serialized.className);
                return GSON.fromJson(serialized.entityString, (Class<E>) clazz);
            } catch (Exception e) {
                log.error("Deserialize exception: ", e);
            }
        }
        return null;
    }

    private static class SerializedEntity {

        private String className;
        private String entityString;

        SerializedEntity(String className, String string) {
            this.className = className;
            this.entityString = string;
        }
    }

    private static class DeserializeException extends IllegalArgumentException{

        DeserializeException(Throwable cause) {
            super(cause);
        }
    }

}
