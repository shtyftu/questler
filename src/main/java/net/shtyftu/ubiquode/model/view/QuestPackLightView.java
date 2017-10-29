package net.shtyftu.ubiquode.model.view;

import net.shtyftu.ubiquode.model.QuestPack;

/**
 * @author shtyftu
 */
public class QuestPackLightView {

    private final String id;
    private final String name;
    private final int questCount;

    public QuestPackLightView(QuestPack questPack) {
        this(questPack.getId(), questPack.getName(), questPack.getProtoIdsByQuestId().entrySet().size());
    }

    private QuestPackLightView(String id, String name, int questCount) {
        this.id = id;
        this.name = name;
        this.questCount = questCount;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getQuestCount() {
        return questCount;
    }
}
