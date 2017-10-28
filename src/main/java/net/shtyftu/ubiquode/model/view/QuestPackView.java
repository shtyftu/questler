package net.shtyftu.ubiquode.model.view;

import java.util.Map;
import net.shtyftu.ubiquode.model.QuestPack;

/**
 * @author shtyftu
 */
public class QuestPackView {

    private final String id;
    private final String name;
    private final Map<String, String> questNamesById;

    public QuestPackView(QuestPack pack, Map<String, String> questNamesById) {
        this(pack.getId(), pack.getName(), questNamesById);
    }

    private QuestPackView(String id, String name, Map<String, String> questNamesById) {
        this.id = id;
        this.name = name;
        this.questNamesById = questNamesById;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Map<String, String> getQuestNamesById() {
        return questNamesById;
    }
}
