package net.shtyftu.ubiquode.model.persist.simple;

/**
 * @author shtyftu
 */
public class QuestProto extends ModelWithId {

    private String name;
    private Long cooldown;
    private Long deadline;
    private int scores;

    public QuestProto(String id, String name, Long cooldown, Long deadline, int scores) {
        super(id);
        this.name = name;
        this.cooldown = cooldown;
        this.deadline = deadline;
        this.scores = scores;
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
