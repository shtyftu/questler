package net.shtyftu.ubiquode.model.view;

import java.util.Map;
import net.shtyftu.ubiquode.model.QuestPack;

/**
 * @author shtyftu
 */
public class QuestPackLightView {

    private final String id;
    private final String name;
    private final Map<String, String> protoIdsByQuestName;

    public QuestPackLightView(QuestPack questPack, Map<String, String> protoIdsByQuestName) {
        this(
                questPack.getId(),
                questPack.getName(),
                protoIdsByQuestName);
    }

    private QuestPackLightView(String id, String name, Map<String, String> protoIdsByQuestName) {
        this.id = id;
        this.name = name;
        this.protoIdsByQuestName = protoIdsByQuestName;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Map<String, String> getProtoIdsByQuestName() {
        return protoIdsByQuestName;
    }
}
