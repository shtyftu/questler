package net.shtyftu.ubiquode.service;

import java.util.UUID;
import net.shtyftu.ubiquode.processor.QuestPackProjector;
import net.shtyftu.ubiquode.processor.QuestProjector;
import net.shtyftu.ubiquode.processor.UserProjector;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author shtyftu
 */
@Service
public class QuestPackService {

    private final QuestPackProjector questPackProjector;
    private final QuestProjector questProjector;
    private final QuestService questService;
    private final UserProjector userProjector;

    @Autowired
    public QuestPackService(QuestPackProjector questPackProjector, QuestProjector questProjector,
            QuestService questService, UserProjector userProjector) {
        this.questPackProjector = questPackProjector;
        this.questProjector = questProjector;
        this.questService = questService;
        this.userProjector = userProjector;
    }

    public String create(String name, String userId) {
        final String packId = UUID.randomUUID().toString();
        questPackProjector.setName(packId, name);

        final long eventTime = questPackProjector.addUser(packId, userId, userId);
        userProjector.addNewQuestPack(userId, packId, eventTime);
        return packId;
    }

    public boolean addQuest(String protoId, String packId, String userId) {
        final String questId = questPackProjector.addQuest(packId, protoId, userId);
        if (StringUtils.isBlank(questId)) {
            return false;
        }
        questProjector.setProtoId(questId, protoId);
        questService.enable(questId, packId);
        return true;
    }

    public void addUser(String packId, String userId, String inviterId) {
        final long eventTime = questPackProjector.addUser(packId, userId, inviterId);
        userProjector.addQuestPack(userId, packId, inviterId, eventTime);
    }
}
