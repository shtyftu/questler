package net.shtyftu.ubiquode.model.persist.simple;

import net.shtyftu.ubiquode.model.AModel;

/**
 * @author shtyftu
 */
public class QuestProto extends AModel implements PersistEntity {

    private String id;
    private String name;
    private Long cooldown;
    private Long deadline;
    private int scores;

    public QuestProto(String id, String name, Long cooldown, Long deadline, int scores) {
        this.id = id;
        this.name = name;
        this.cooldown = cooldown;
        this.deadline = deadline;
        this.scores = scores;
    }

    @Override
    public String getKey() {
        return id;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Long getCooldown() {
        return cooldown;
    }

    public Long getDeadline() {
        return deadline;
    }

    public int getScores() {
        return scores;
    }
}
