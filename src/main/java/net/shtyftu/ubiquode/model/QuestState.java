package net.shtyftu.ubiquode.model;

/**
 * @author shtyftu
 */
public class QuestState {

    private String id;
    private Long deadlineAt;
    private Long cooldownTill;
    private State state;
    private transient QuestProto proto;

    public QuestState(QuestProto proto, Long deadlineAt, Long cooldownTill, State state) {
        this.id = proto.getId();
        this.deadlineAt = deadlineAt;
        this.cooldownTill = cooldownTill;
        this.state = state;
        this.proto = proto;
    }

    public String getId() {
        return id;
    }

    public Long getDeadlineAt() {
        return deadlineAt;
    }

    public Long getCooldownTill() {
        return cooldownTill;
    }

    public State getState() {
        return state;
    }

    public QuestProto getProto() {
        return proto;
    }

    public enum State {
        Available,
        OnCooldown,
        LockedByUser
    }


}
