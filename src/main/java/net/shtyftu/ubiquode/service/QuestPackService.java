package net.shtyftu.ubiquode.service;

import net.shtyftu.ubiquode.processor.QuestPackProcessor;
import net.shtyftu.ubiquode.processor.QuestProcessor;
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

    @Autowired
    public QuestPackService(QuestPackProcessor questPackProcessor, QuestProcessor questProcessor) {
        this.questPackProcessor = questPackProcessor;
        this.questProcessor = questProcessor;
    }

    public boolean addQuestToPack(String protoId, String packId, String userId) {
        final String questId = questPackProcessor.addQuest(packId, protoId, userId);
        if (StringUtils.isBlank(questId)) {
            return false;
        }
        questProcessor.setProtoId(questId, protoId);
        return true;
    }

}
