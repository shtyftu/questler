package net.shtyftu.ubiquode.model;

/**
 * @author shtyftu
 */
public class QuestProto {

    private String id;
    private String name;
    private Long cooldown;
    private Long deadline;

    public QuestProto(String id, String name, Long cooldown, Long deadline) {
        this.id = id;
        this.name = name;
        this.cooldown = cooldown;
        this.deadline = deadline;
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
}
