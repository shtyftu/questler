package net.shtyftu.ubiquode.model.projection;

import java.util.ArrayList;
import java.util.List;
import net.shtyftu.ubiquode.model.AModel;

/**
 * @author shtyftu
 */
public class User extends AModel {

    private String name;
    private String lockedQuestId;
    private Long mustCompleteQuestTill;
    private Long unableToLockQuestTill;
    private List<String> questPackIds;

    public User(String name) {
        this.name = name;
        this.questPackIds = new ArrayList<>();
    }

    public boolean isCanLockQuest() {
        return lockedQuestId == null
                && (unableToLockQuestTill == null || System.currentTimeMillis() > unableToLockQuestTill);
    }

    public String getLockedQuestId() {
        return lockedQuestId;
    }

    public void setLockedQuestId(String lockedQuest) {
        this.lockedQuestId = lockedQuest;
    }

    public void setMustCompleteQuestTill(Long mustCompleteQuestTill) {
        this.mustCompleteQuestTill = mustCompleteQuestTill;
    }

    public void setUnableToLockQuestTill(Long unableToLockQuest) {
        this.unableToLockQuestTill = unableToLockQuest;
    }

    public boolean isCanCompleteQuest(String questId) {
        return lockedQuestId != null && lockedQuestId.equals(questId)
                && mustCompleteQuestTill != null && now() < mustCompleteQuestTill;
    }

    public List<String> getQuestPackIds() {
        return questPackIds;
    }

}
