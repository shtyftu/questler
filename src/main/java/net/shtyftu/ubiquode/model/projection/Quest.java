package net.shtyftu.ubiquode.model.projection;

import net.shtyftu.ubiquode.dao.plain.QuestProtoDao;
import net.shtyftu.ubiquode.model.AModel;
import net.shtyftu.ubiquode.model.persist.simple.QuestProto;
import net.shtyftu.ubiquode.service.ConfigService;
import org.apache.commons.lang3.StringUtils;

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
    private boolean waitTrigger;
    private transient final QuestProtoDao protoDao;
    private transient QuestProto proto;

    public Quest(String id, QuestProtoDao protoDao) {
        this.id = id;
        this.protoDao = protoDao;
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
        if (waitTrigger) {
            return State.WaitingTrigger;
        }
        if (lockedTill != null && now() < lockedTill) {
            return State.LockedByUser;
        }
        if (cooldownTill != null && now() < cooldownTill) {
            return State.OnCooldown;
        }
        if (deadlineAt != null){
            long now = now();
            long deadlinePanicAt = deadlineAt - ConfigService.PANIC_TIME_BEFORE_DEADLINE;
            if (now > deadlinePanicAt) {
                return State.DeadlinePanic;
            }
        }
        return State.Available;
    }


    public QuestProto getProto() {
        if (proto == null) {
            if (StringUtils.isBlank(protoId)) {
                throw new IllegalStateException("there is no protoId for quest [" + id + "]");
            }
            final QuestProto proto = protoDao.getById(protoId);
            if (proto == null) {
                throw new IllegalStateException(
                        "there is no proto for protoId [" + protoId + "] for questId [" + id + "]");
            }
            this.proto = proto;
        }
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

    public boolean isWaitTrigger() {
        return waitTrigger;
    }

    public void setWaitTrigger(boolean waitTrigger) {
        this.waitTrigger = waitTrigger;
    }

    public enum State {
        LockedByUser(8),
        DeadlinePanic(15),
        Available(20),
        WaitingTrigger(23),
//        Unavailable(25, "Unavailable"),
        OnCooldown(30),
        Disabled(34);

        private final int order;

        State(int order) {
            this.order = order;
        }

        public int getOrder() {
            return order;
        }

    }

}
