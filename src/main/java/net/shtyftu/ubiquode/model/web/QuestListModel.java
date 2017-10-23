package net.shtyftu.ubiquode.model.web;

import java.util.List;

/**
 * @author shtyftu
 */
public class QuestListModel {

    final String id;
    final String name;
    final List<String> quests;

    public QuestListModel(String id, String name, List<String> quests) {
        this.id = id;
        this.name = name;
        this.quests = quests;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public List<String> getQuests() {
        return quests;
    }
}
