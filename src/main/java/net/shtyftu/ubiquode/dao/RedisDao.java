package net.shtyftu.ubiquode.dao;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.lambdaworks.redis.RedisClient;
import com.lambdaworks.redis.api.sync.RedisCommands;
import com.lambdaworks.redis.api.sync.RedisStringCommands;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Consumer;
import java.util.stream.Collectors;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author shtyftu
 */
public abstract class RedisDao<E> {

    private static final int CONNECTION_MAX_COUNT = 50;
    private static final String REDIS_HOST = "redis://localhost";
    private static final Gson GSON = new Gson();

    private static final RedisClient client = RedisClient.create(REDIS_HOST);
    private static final List<RedisCommands<String, String>> connectionPool = new ArrayList<>();
    private static final AtomicInteger connectionCounter = new AtomicInteger();

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
        synchronized (connectionPool) {
            int counter = connectionCounter.get();
            if (counter == CONNECTION_MAX_COUNT) {
                counter = connectionCounter.addAndGet(-CONNECTION_MAX_COUNT);
            }

            final int size = connectionPool.size();
            if (size < counter + 1) {
                connectionPool.add(client.connect().sync());
                return redisCmd();
            }
            connectionCounter.incrementAndGet();
            return connectionPool.get(counter);
        }
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

}
