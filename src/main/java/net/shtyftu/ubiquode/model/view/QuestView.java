package net.shtyftu.ubiquode.model.view;

import net.shtyftu.ubiquode.model.QuestPack;
import net.shtyftu.ubiquode.model.projection.Quest;
import net.shtyftu.ubiquode.model.projection.Quest.State;

/**
 * @author shtyftu
 */
public class QuestView implements Comparable<QuestView> {

    private final String id;
    private final String name;
    private final String packId;
    private final String packName;
    private final String state;
    private final String actionName;
    private final String actionLink;
    private final Long time;
    private final Integer scores;
    private transient final int order;

    public QuestView(String userId, Quest quest, QuestPack pack) {
        this.id = quest.getId();
        this.name = quest.getProto().getName();
        this.packId = pack.getId();
        this.packName = pack.getName();
        this.scores = quest.getProto().getScores();
        final State state = quest.getState();
        this.state = state.name();
        this.order = state.getOrder();

        switch (state) {
            case Available:
                this.time = quest.getDeadlineAt();
                this.actionLink = getLink("lock");
                this.actionName = "Lock";
                break;
            case DeadlinePanic:
                this.time = quest.getDeadlineAt();
                this.actionLink = getLink("complete");
                this.actionName = "Complete";
                break;
            case OnCooldown:
                this.time = quest.getCooldownTill();
                this.actionLink = "";
                this.actionName = "";
                break;
            case WaitingTrigger:
                this.time = null;
                this.actionLink = getLink("trigger");
                this.actionName = "Trigger";
                break;
            case Disabled:
                this.time = null;
                this.actionLink = getLink("enable");
                this.actionName = "Enable";
                break;
            case LockedByUser:
                this.time = quest.getLockedTill();
                if (quest.getUserId().equals(userId)) {
                    this.actionLink = getLink("complete");
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

    private String getLink(String action) {
        return "/quest/" +action + "?questId=" + id +"&packId=" + packId;
    }

    public String getId() {
        return id;
    }

    public String getPackId() {
        return packId;
    }

    public String getPackName() {
        return packName;
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

    public Integer getScores() {
        return scores;
    }

    @Override
    public int compareTo(QuestView other) {
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
