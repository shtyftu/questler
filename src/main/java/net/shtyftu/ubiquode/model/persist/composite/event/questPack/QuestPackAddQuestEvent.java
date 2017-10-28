package net.shtyftu.ubiquode.model.persist.composite.event.questPack;

import java.util.UUID;
import net.shtyftu.ubiquode.model.QuestPack;

/**
 * @author shtyftu
 */
public class QuestPackAddQuestEvent extends QuestPackEvent {

    private final String userId;
    private final String questId;

    public QuestPackAddQuestEvent(String packId, String userId) {
        super(packId);
        this.userId = userId;
        this.questId = UUID.randomUUID().toString();
    }

    @Override
    public void applyTo(QuestPack questPack) {
        questPack.getQuestIdList().add(questId);
    }

    public String getQuestId() {
        return questId;
    }
}
