package net.shtyftu.ubiquode.model.projection;

import net.shtyftu.ubiquode.model.Model;
import net.shtyftu.ubiquode.model.persist.simple.QuestProto;
import net.shtyftu.ubiquode.service.ConfigService;

/**
 * @author shtyftu
 */
public class QuestState extends Model {

    private Long deadlineAt;
    private Long cooldownTill;
    private Long lockedTill;
    private boolean enabled;
    private transient QuestProto proto;

    public QuestState(QuestProto proto) {
        this.proto = proto;
    }

    public void setDeadlineAt(Long deadlineAt) {
        this.deadlineAt = deadlineAt;
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

    public enum State {
        Disabled,
        Available,
        OnCooldown,
        LockedByUser,
        DeadlinePanic
    }

}
