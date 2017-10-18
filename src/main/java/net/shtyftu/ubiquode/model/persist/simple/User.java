package net.shtyftu.ubiquode.model.persist.simple;

/**
 * @author shtyftu
 */
public class User implements PersistEntity<String> {

    private String name;
    private Long scores;
    private String lockedQuest;

    public User(String name) {
        this.name = name;
    }

    public void setLockedQuestId(String lockedQuest) {
        this.lockedQuest = lockedQuest;
    }

    public String getLockedQuest() {
        return lockedQuest;
    }

    @Override
    public String getKey() {
        return name;
    }
}
