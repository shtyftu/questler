package net.shtyftu.ubiquode.model.projection;

import net.shtyftu.ubiquode.model.AModel;
import net.shtyftu.ubiquode.model.persist.simple.QuestProto;
import net.shtyftu.ubiquode.service.ConfigService;

/**
 * @author shtyftu
 */
public class Quest extends AModel {

    private final String id;
    private Long deadlineAt;
    private Long cooldownTill;
    private Long lockedTill;
    private String userId;
    private boolean enabled;
    private String protoId;
    private transient QuestProto proto;

    public Quest(String id) {
        this.id = id;
    }

    public void setDeadlineAt(Long deadlineAt) {
        this.deadlineAt = deadlineAt;
    }

    public Long getLockedTill() {
        return lockedTill;
    }

    public void setCooldownTill(Long cooldownTill) {
        this.cooldownTill = cooldownTill;
    }

    public void setLockedTill(Long lockedTill) {
        this.lockedTill = lockedTill;
    }

    public State getState() {
        if (!enabled) {
            return State.Disabled;
        }
        if (lockedTill != null && now() < lockedTill) {
            return State.LockedByUser;
        }
        if (cooldownTill != null && now() < cooldownTill) {
            return State.OnCooldown;
        }
        if (deadlineAt != null && now() > deadlineAt + ConfigService.PANIC_TIME_BEFORE_DEADLINE) {
            return State.DeadlinePanic;
        }
        return State.Available;
    }


    public QuestProto getProto() {
        return proto;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public Long getCooldownTill() {
        return cooldownTill;
    }

    public Long getDeadlineAt() {
        return deadlineAt;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public void setProtoId(String protoId) {
        this.protoId = protoId;
    }

    public String getProtoId() {
        return protoId;
    }

    public void setProto(QuestProto proto) {
        this.proto = proto;
    }

    public String getId() {
        return id;
    }

    public enum State {
        Disabled(24),
        Available(20),
        OnCooldown(30),
        LockedByUser(26),
        DeadlinePanic(10);

        private final int order;

        State(int order) {
            this.order = order;
        }

        public int getOrder() {
            return order;
        }
    }

}
