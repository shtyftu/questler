package net.shtyftu.ubiquode.model.view;

import net.shtyftu.ubiquode.model.persist.simple.QuestProto;

/**
 * @author shtyftu
 */
public class QuestProtoView {

    final String id;
    final UpdateStatus status;
    final String description;

    public QuestProtoView(QuestProto questProto) {
        this(questProto, UpdateStatus.Old);
    }

    public QuestProtoView(QuestProto questProto, UpdateStatus status) {
        this(questProto.getId(), status, questProto.toString());
    }

    private QuestProtoView(String id, UpdateStatus status, String description) {
        this.id = id;
        this.status = status;
        this.description = description;
    }

    public String getId() {
        return id;
    }

    public UpdateStatus getStatus() {
        return status;
    }

    public String getDescription() {
        return description;
    }

    public enum UpdateStatus {
        Old,
        Created,
        Updated
    }
}
