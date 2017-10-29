package net.shtyftu.ubiquode.model.view;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import net.shtyftu.ubiquode.model.QuestPack;

/**
 * @author shtyftu
 */
public class QuestPackView {

    private String id;
    private String name;
    private Map<String, String> questNamesById = new HashMap<>();

//    private List<String> questIds;
    private List<String> protoIds;

    @SuppressWarnings("unused")
    public QuestPackView() {
    }

    public QuestPackView(QuestPack pack, Map<String, String> questNamesById) {
        this(pack.getId(), pack.getName(), questNamesById);
    }

    private QuestPackView(String id, String name, Map<String, String> questNamesById) {
        this.id = id;
        this.name = name;
        this.questNamesById = questNamesById;
    }

    @SuppressWarnings("unused")
    public void setId(String id) {
        this.id = id;
    }

    @SuppressWarnings("unused")
    public String getId() {
        return id;
    }

    @SuppressWarnings("unused")
    public String getName() {
        return name;
    }

    public Map<String, String> getQuestNamesById() {
        return questNamesById;
    }

    @SuppressWarnings("unused")
    public void setQuestNamesById(Map<String, String> questNamesById) {
        this.questNamesById = questNamesById;
    }

    //    public List<String> getQuestIds() {
//        return questIds;
//    }
//
//    @SuppressWarnings("unused")
//    public void setQuestIds(List<String> questIds) {
//        this.questIds = questIds;
//    }

    public List<String> getProtoIds() {
        return protoIds;
    }

    @SuppressWarnings("unused")
    public void setProtoIds(List<String> protoIds) {
        this.protoIds = protoIds;
    }
}
