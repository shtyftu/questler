package net.shtyftu.ubiquode.dao.list.event;

import com.google.gson.reflect.TypeToken;
import net.shtyftu.ubiquode.dao.RedisClientService;
import net.shtyftu.ubiquode.dao.list.RedisListDao;
import net.shtyftu.ubiquode.model.persist.composite.event.AEvent;

/**
 * @author shtyftu
 */
public abstract class RedisEventDao<E extends AEvent<?>> extends RedisListDao<E> implements EventDao<E> {

    public RedisEventDao(TypeToken<E> typeToken, RedisClientService clientService) {
        super(typeToken, clientService);
    }
}
