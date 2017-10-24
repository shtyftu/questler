package net.shtyftu.ubiquode.model.dump;

import java.util.List;
import net.shtyftu.ubiquode.model.persist.simple.QuestProto;

/**
 * @author shtyftu
 */
public class QuestListDump {

    private String id;
    private List<QuestProto> list;

    public QuestListDump() {
    }

    public QuestListDump(String id, List<QuestProto> list) {
        this.id = id;
        this.list = list;
    }

    public String getId() {
        return id;
    }

    public List<QuestProto> getList() {
        return list;
    }
}
