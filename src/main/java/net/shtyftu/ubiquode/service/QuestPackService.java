package net.shtyftu.ubiquode.service;

import net.shtyftu.ubiquode.processor.QuestPackProcessor;
import net.shtyftu.ubiquode.processor.QuestProcessor;
import net.shtyftu.ubiquode.processor.UserProcessor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author shtyftu
 */
@Service
public class QuestPackService {

    private final QuestPackProcessor questPackProcessor;
    private final QuestProcessor questProcessor;
    private final UserProcessor userProcessor;

    @Autowired
    public QuestPackService(QuestPackProcessor questPackProcessor, QuestProcessor questProcessor,
            UserProcessor userProcessor) {
        this.questPackProcessor = questPackProcessor;
        this.questProcessor = questProcessor;
        this.userProcessor = userProcessor;
    }

    public boolean addQuest(String protoId, String packId, String userId) {
        final String questId = questPackProcessor.addQuest(packId, protoId, userId);
        if (StringUtils.isBlank(questId)) {
            return false;
        }
        questProcessor.setProtoId(questId, protoId);
        return true;
    }

    public void addUser(String packId, String userId, String inviterId) {
        userProcessor.addQuestPack(userId, packId, inviterId);
        questPackProcessor.addUser(packId, userId, inviterId);
    }
}
