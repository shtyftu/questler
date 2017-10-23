package net.shtyftu.ubiquode.model.persist.simple;

import java.util.List;
import net.shtyftu.ubiquode.model.AModel;

/**
 * @author shtyftu
 */
public class QuestList extends AModel implements PersistEntity {

    private String id;
    private List<String> questIdList;

    public QuestList(String id, List<String> questIdList) {
        this.id = id;
        this.questIdList = questIdList;
    }

    @Override
    public String getKey() {
        return id;
    }

    public List<String> getQuestIdList() {
        return questIdList;
    }
}
