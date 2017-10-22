package net.shtyftu.ubiquode.model.web;

import net.shtyftu.ubiquode.model.projection.QuestState;
import net.shtyftu.ubiquode.model.projection.QuestState.State;

/**
 * @author shtyftu
 */
public class QuestModel {

    private final String id;
    private final String name;
    private final String state;
    private final Long time;

    public QuestModel(QuestState questState) {
        this.id = questState.getProto().getId();
        this.name = questState.getProto().getName();
        final State state = questState.getState();
        this.state = state.name();
        if (State.OnCooldown.equals(state)) {
            this.time = questState.getCooldownTill();
        } else if (State.Available.equals(state) || State.DeadlinePanic.equals(state)) {
            this.time = questState.getDeadlineAt();
        } else {
            this.time = null;
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
}
