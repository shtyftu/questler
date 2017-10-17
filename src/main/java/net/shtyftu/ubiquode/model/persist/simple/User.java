package net.shtyftu.ubiquode.model.persist.simple;

/**
 * @author shtyftu
 */
public class User {

    private String name;
    private Long scores;
    private String lockedQuest;

    public void setLockedQuestId(String lockedQuest) {
        this.lockedQuest = lockedQuest;
    }

    public String getLockedQuest() {
        return lockedQuest;
    }
}
