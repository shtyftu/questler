package net.shtyftu.ubiquode.dao;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.lambdaworks.redis.api.sync.RedisCommands;
import java.util.Arrays;
import java.util.stream.Collectors;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author shtyftu
 */
public abstract class RedisDao<E> {

    protected final String redisTypeName;

    private static final Gson GSON = new Gson();

    private final RedisClientService clientService;
    private final Logger log = LoggerFactory.getLogger(this.getClass());

    //todo change type token to class
    public RedisDao(TypeToken<E> typeToken, RedisClientService clientService) {
        final String typeString = typeToken.getType().toString();
        final String className = (typeString.substring(typeString.lastIndexOf(".") + 1));
        redisTypeName = Arrays.stream(className.split("(?=\\p{Upper})"))
                .map(String::toLowerCase)
                .collect(Collectors.joining("-"));
        this.clientService = clientService;
    }

    protected RedisCommands<String, String> redisCmd() {
        return clientService.redisCmd();
    }

    protected String serialize(E e) {
        final String className = e.getClass().getName();
        final SerializedEntity entity = new SerializedEntity(className, GSON.toJson(e));
        return GSON.toJson(entity);
    }

    @SuppressWarnings("unchecked")
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
