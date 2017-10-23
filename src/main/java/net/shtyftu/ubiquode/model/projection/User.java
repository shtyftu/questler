package net.shtyftu.ubiquode.model.projection;

import net.shtyftu.ubiquode.model.AModel;

/**
 * @author shtyftu
 */
public class User extends AModel {

    private String name;
    private Long scores;
    private String lockedQuestId;
    private Long mustCompleteQuestTill;
    private Long unableToLockQuestTill;
    private int score;

    public User(String name) {
        this.name = name;
    }

    public boolean isCanLockQuest() {
        return unableToLockQuestTill == null || System.currentTimeMillis() > unableToLockQuestTill;
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

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public boolean isNeedToDealWithDeadline() {
        return true;
    }
}
