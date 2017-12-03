package net.shtyftu.ubiquode.model.view;

import net.shtyftu.ubiquode.model.persist.simple.QuestProto;

/**
 * @author shtyftu
 */
public class QuestProtoLegacyView {

    final String id;
    final UpdateStatus status;
    final String description;

    public QuestProtoLegacyView(QuestProto questProto) {
        this(questProto, UpdateStatus.Old);
    }

    public QuestProtoLegacyView(QuestProto questProto, UpdateStatus status) {
        this(questProto.getId(), status, questProto.toString());
    }

    private QuestProtoLegacyView(String id, UpdateStatus status, String description) {
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
