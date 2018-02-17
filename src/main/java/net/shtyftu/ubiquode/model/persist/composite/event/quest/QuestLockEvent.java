package net.shtyftu.ubiquode.model.persist.composite.event.quest;

import net.shtyftu.ubiquode.model.projection.Quest;
import net.shtyftu.ubiquode.service.ConfigService;

/**
 * @author shtyftu
 */
public class QuestLockEvent extends QuestEvent {

    private final String userId;

    public QuestLockEvent(String questId, String userId) {
        super(questId);
        this.userId = userId;
    }

    @Override
    public void applyTo(Quest quest) {
        quest.setLockedTill(getTime() + ConfigService.QUEST_LOCK_TIME);
        quest.setUserId(userId);
    }

    @Override
    public String getViewName() {
        return "Lock";
    }
}
