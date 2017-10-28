package net.shtyftu.ubiquode.model.dump;

import java.util.List;
import net.shtyftu.ubiquode.model.persist.simple.QuestProto;

/**
 * @author shtyftu
 */
public class QuestProtoDump {

    private List<QuestProto> list;

    public QuestProtoDump() {
    }

    public QuestProtoDump(List<QuestProto> list) {
        this.list = list;
    }

    public List<QuestProto> getList() {
        return list;
    }
}
