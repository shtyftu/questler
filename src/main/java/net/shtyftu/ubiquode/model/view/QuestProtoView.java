package net.shtyftu.ubiquode.model.view;

import net.shtyftu.ubiquode.model.persist.simple.QuestProto;

public class QuestProtoView extends QuestProto {

    private String packId;

    public String getPackId() {
        return packId;
    }

    public void setPackId(String packId) {
        this.packId = packId;
    }
}
