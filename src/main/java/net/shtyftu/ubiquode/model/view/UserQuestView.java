package net.shtyftu.ubiquode.model.view;

import net.shtyftu.ubiquode.model.projection.Quest;
import net.shtyftu.ubiquode.model.projection.Quest.State;

/**
 * @author shtyftu
 */
public class UserQuestView implements Comparable<UserQuestView> {

    private final String id;
    private final String name;
    private final String packName;
    private final String state;
    private final String actionName;
    private final String actionLink;
    private final Long time;
    private transient final int order;

    public UserQuestView(Quest quest, String userId, String packName) {
        this.id = quest.getId();
        this.name = quest.getProto().getName();
        this.packName = packName;
        final State state = quest.getState();
        this.state = state.name();
        this.order = state.getOrder();

        switch (state) {
            case Available:
                this.time = quest.getDeadlineAt();
                this.actionLink = "/quest/lock?questId=" + id;
                this.actionName = "Lock";
                break;
            case DeadlinePanic:
                this.time = quest.getDeadlineAt();
                this.actionLink = "/quest/complete?questId=" + id;
                this.actionName = "Complete";
                break;
            case OnCooldown:
                this.time = quest.getCooldownTill();
                this.actionLink = "";
                this.actionName = "";
                break;
            case Disabled:
                this.time = null;
                this.actionLink = "/quest/enable?questId=" + id;
                this.actionName = "Enable";
                break;
            case LockedByUser:
                this.time = quest.getLockedTill();
                if (quest.getUserId().equals(userId)) {
                    this.actionLink = "/quest/complete?questId=" + id;
                    this.actionName = "Complete";
                } else {
                    this.actionLink = "";
                    this.actionName = "";
                }
                break;
            default:
                throw new UnsupportedOperationException();
        }

    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getState() {
        return state;
    }

    public Long getTime() {
        return time;
    }

    public String getActionName() {
        return actionName;
    }

    public String getActionLink() {
        return actionLink;
    }

    @Override
    public int compareTo(UserQuestView other) {
        if (other == null) {
            return -1;
        }
        final int stateCompare = Integer.compare(this.order, other.order);
        if (stateCompare != 0) {
            return stateCompare;
        }
        if (this.time == null || other.time == null) {
            return id.compareTo(other.id);
        }

        return Long.compare(this.time, other.time);
    }
}
