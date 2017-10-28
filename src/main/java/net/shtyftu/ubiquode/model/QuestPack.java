package net.shtyftu.ubiquode.model;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

/**
 * @author shtyftu
 */
public class QuestPack extends AModel {

    private final String id;
    //TODO change to proto Map
    private List<String> questIdList;
    private String name;
    private Map<String, Integer> userScores;

    public QuestPack(String id) {
        this.id = id;
        questIdList = new ArrayList<>();
        userScores = new HashMap<>();
    }

    public String getId() {
        return id;
    }

    public List<String> getQuestIdList() {
        return questIdList;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Map<String, Integer> getUserScores() {
        return userScores;
    }

    public void setUserScores(Map<String, Integer> userScores) {
        this.userScores = userScores;
    }

    public String getLowestScoreUser() {
        return getUserScores().entrySet().stream()
                .sorted(Comparator.comparing(Entry::getValue))
                .limit(1)
                .map(Entry::getKey)
                .findFirst().orElse(null);
    }
}
