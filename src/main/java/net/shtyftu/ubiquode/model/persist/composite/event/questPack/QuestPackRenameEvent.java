package net.shtyftu.ubiquode.model.persist.composite.event.questPack;

import net.shtyftu.ubiquode.model.QuestPack;

/**
 * @author shtyftu
 */
public class QuestPackRenameEvent extends QuestPackEvent {

    private final String name;

    public QuestPackRenameEvent(String packId, String name) {
        super(packId);
        this.name = name;
    }

    @Override
    public void applyTo(QuestPack entity) {
        entity.setName(name);
    }
}
