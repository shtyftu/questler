package net.shtyftu.ubiquode.model.persist.simple;

/**
 * @author shtyftu
 */
public class QuestProto implements PersistEntity<String> {

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
}
