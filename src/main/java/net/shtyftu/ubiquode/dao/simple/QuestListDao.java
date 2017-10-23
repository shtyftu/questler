package net.shtyftu.ubiquode.dao.simple;

import java.util.ArrayList;
import net.shtyftu.ubiquode.model.persist.simple.QuestList;
import org.springframework.stereotype.Component;

/**
 * @author shtyftu
 */
@Component
public class QuestListDao extends HashMapDao<QuestList> implements Dao<QuestList> {

    public QuestListDao() {
        final ArrayList<String> idList = new ArrayList<String>() {{
            add("id1");
            add("id2");
        }};
        save(new QuestList("default", idList));
    }
}
