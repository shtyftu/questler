package net.shtyftu.ubiquode.dao;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import net.shtyftu.ubiquode.model.QuestProto;

/**
 * @author shtyftu
 */
public class QuestProtoDao implements Dao<QuestProto, String> {

    private Map<String, QuestProto> PROTOS = new HashMap<String, QuestProto>(){{
       put("id1", new QuestProto("id1", "clean table", TimeUnit.HOURS.toMillis(6), TimeUnit.HOURS.toMillis(24)));
       put("id2", new QuestProto("id2", "move clothes", TimeUnit.HOURS.toMillis(12), TimeUnit.HOURS.toMillis(48)));
    }};

    public Collection<QuestProto> getAll() {
        return PROTOS.values();
    }

    @Override
    public QuestProto getByKey(String key) {
        return getAll().stream().filter(q -> key.equals(q.getId())).findFirst().orElse(null);
    }

    public Set<String> getAllKeys() {
        return PROTOS.keySet();
    }
}
