package net.shtyftu.ubiquode.service;

import java.util.UUID;
import net.shtyftu.ubiquode.processor.QuestPackRepository;
import net.shtyftu.ubiquode.processor.QuestRepository;
import net.shtyftu.ubiquode.processor.UserRepository;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author shtyftu
 */
@Service
public class QuestPackService {

    private final QuestPackRepository questPackProjector;
    private final QuestRepository questProjector;
    private final QuestService questService;
    private final UserRepository userProjector;

    @Autowired
    public QuestPackService(QuestPackRepository questPackProjector, QuestRepository questProjector,
            QuestService questService, UserRepository userProjector) {
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
