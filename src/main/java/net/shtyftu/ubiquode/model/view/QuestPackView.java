package net.shtyftu.ubiquode.model.view;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import net.shtyftu.ubiquode.model.QuestPack;

/**
 * @author shtyftu
 */
public class QuestPackView {

    private String id;
    private String name;
    private Map<String, String> questNamesById = new HashMap<>();
    private Map<String, String> userNamesById = new HashMap<>();

    private List<String> protoIds;
    private List<String> inviteIds;

    @SuppressWarnings("unused")
    private QuestPackView() {
    }

    public QuestPackView(QuestPack pack, Map<String, String> questNamesById, Map<String, String> userNamesById ) {
        this(pack.getId(), pack.getName(), questNamesById, userNamesById);
    }

    private QuestPackView(String id, String name,
            Map<String, String> questNamesById, Map<String, String> userNamesById ) {
        this.id = id;
        this.name = name;
        this.questNamesById = questNamesById;
        this.userNamesById = userNamesById;
    }

    @SuppressWarnings("unused")
    public void setId(String id) {
        this.id = id;
    }

    @SuppressWarnings("unused")
    public String getId() {
        return id;
    }

    @SuppressWarnings("unused")
    public String getName() {
        return name;
    }

    public Map<String, String> getQuestNamesById() {
        return questNamesById;
    }

    @SuppressWarnings("unused")
    public void setQuestNamesById(Map<String, String> questNamesById) {
        this.questNamesById = questNamesById;
    }

    public Map<String, String> getUserNamesById() {
        return userNamesById;
    }

    @SuppressWarnings("unused")
    public void setUserNamesById(Map<String, String> userNamesById) {
        this.userNamesById = userNamesById;
    }

    public List<String> getProtoIds() {
        return protoIds;
    }

    @SuppressWarnings("unused")
    public void setProtoIds(List<String> protoIds) {
        this.protoIds = protoIds;
    }

    public List<String> getInviteIds() {
        return inviteIds;
    }

    @SuppressWarnings("unused")
    public void setInviteIds(List<String> inviteIds) {
        this.inviteIds = inviteIds;
    }
}
