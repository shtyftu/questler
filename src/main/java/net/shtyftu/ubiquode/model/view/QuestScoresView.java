package net.shtyftu.ubiquode.model.view;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Collectors;
import net.shtyftu.ubiquode.model.QuestPack;

/**
 * @author shtyftu
 */
public class QuestScoresView {

    private final String packName;
    private final Map<String, Integer> scores;

    public QuestScoresView(QuestPack pack) {
        this(pack.getName(), pack.getUserScores());
    }

    private QuestScoresView(String packName, Map<String, Integer> scores) {
        this.packName = packName;
        this.scores = scores.entrySet()
                .stream()
                .sorted(Entry.comparingByValue(Collections.reverseOrder()))
                .collect(Collectors.toMap(Entry::getKey, Entry::getValue, (e1, e2) -> e1, LinkedHashMap::new));
    }

    public String getPackName() {
        return packName;
    }

    public Map<String, Integer> getScores() {
        return scores;
    }
}
