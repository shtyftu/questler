package net.shtyftu.ubiquode.model.projection;

import net.shtyftu.ubiquode.model.persist.simple.QuestProto;

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

    public void setId(String id) {
        this.id = id;
    }

    public Long getDeadlineAt() {
        return deadlineAt;
    }

    public void setDeadlineAt(Long deadlineAt) {
        this.deadlineAt = deadlineAt;
    }

    public Long getCooldownTill() {
        return cooldownTill;
    }

    public void setCooldownTill(Long cooldownTill) {
        this.cooldownTill = cooldownTill;
    }

    public State getState() {
        return state;
    }

    public void setState(State state) {
        this.state = state;
    }

    public QuestProto getProto() {
        return proto;
    }

    public void setProto(QuestProto proto) {
        this.proto = proto;
    }

    public enum State {
        Disabled,
        Available,
        OnCooldown,
        LockedByUser
    }


}
