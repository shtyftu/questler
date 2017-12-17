package net.shtyftu.ubiquode.dao;

import com.lambdaworks.redis.RedisClient;
import com.lambdaworks.redis.api.sync.RedisCommands;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

/**
 * @author shtyftu
 */
@Service
public class RedisClientService {

    private static final int CONNECTION_MAX_COUNT = 50;
    private final List<RedisCommands<String, String>> connectionPool = new ArrayList<>();
    private final AtomicInteger connectionCounter = new AtomicInteger();
    private final Logger log = LoggerFactory.getLogger(this.getClass());

    private RedisClient client;

    @Value("${db.redis.host}")
    public void setDatabase(String redisHost) {
        client = RedisClient.create(redisHost);
    }

    public RedisCommands<String, String> redisCmd() {
        synchronized (connectionPool) {
            try {
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
            } catch (Exception e) {
                log.error("Redis error: ", e);
                throw e;
            }
        }
    }
}
