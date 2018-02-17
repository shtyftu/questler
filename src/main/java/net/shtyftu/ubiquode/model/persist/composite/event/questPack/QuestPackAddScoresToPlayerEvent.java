package net.shtyftu.ubiquode.model.persist.composite.event.questPack;

import java.util.Map;
import net.shtyftu.ubiquode.model.QuestPack;

/**
 * @author shtyftu
 */
public class QuestPackAddScoresToPlayerEvent extends QuestPackEvent {

    private final String userId;
    private final int scores;

    public QuestPackAddScoresToPlayerEvent(String packId, String userId, int scores, long eventTime) {
        super(packId, eventTime);
        this.userId = userId;
        this.scores = scores;
    }

    @Override
    public void applyTo(QuestPack questPack) {
        final Map<String, Integer> userScores = questPack.getUserScores();
        final Integer oldScores = userScores.get(userId);
        if (oldScores != null) {
            userScores.put(userId, oldScores + scores);
        }
    }
}
