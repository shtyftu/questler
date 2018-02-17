package net.shtyftu.ubiquode.model.persist.composite.event.quest;

import net.shtyftu.ubiquode.model.projection.Quest;

/**
 * @author shtyftu
 */
public class QuestSetProtoIdEvent extends QuestEvent {

    private final String protoId;

    public QuestSetProtoIdEvent(String questId, String protoId) {
        super(questId);
        this.protoId = protoId;
    }

    @Override
    public void applyTo(Quest quest) {
        quest.setProtoId(protoId);
    }

    @Override
    public String getViewName() {
        return "Set proto id";
    }
}
