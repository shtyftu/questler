package net.shtyftu.ubiquode.processor;

import java.util.UUID;
import net.shtyftu.ubiquode.dao.composite.event.EventDao;
import net.shtyftu.ubiquode.dao.plain.QuestProtoDao;
import net.shtyftu.ubiquode.model.persist.composite.event.user.UserAddQuestPackEvent;
import net.shtyftu.ubiquode.model.persist.composite.event.user.UserEvent;
import net.shtyftu.ubiquode.model.persist.composite.event.user.UserQuestCompleteEvent;
import net.shtyftu.ubiquode.model.persist.composite.event.user.UserQuestLockEvent;
import net.shtyftu.ubiquode.model.persist.simple.QuestProto;
import net.shtyftu.ubiquode.model.projection.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author shtyftu
 */
@Component
public class UserProcessor extends Processor<User, UserEvent> {

    private final QuestProtoDao questProtoDao;

    @Autowired
    public UserProcessor(EventDao<UserEvent> eventDao, QuestProtoDao questProtoDao) {
        super(eventDao);
        this.questProtoDao = questProtoDao;
    }

    @Override
    protected User createNew(String key) {
        return new User(key);
    }

    public void lock(String userId, String questId) {
        save(new UserQuestLockEvent(userId, questId));
    }

    public int complete(String userId, String questId) {
        final QuestProto questProto = questProtoDao.getById(questId);
        final int scores = questProto.getScores();
        save(new UserQuestCompleteEvent(userId, scores));
        return scores;
    }

    public String addNewQuestPack(String userId) {
        final String packId = UUID.randomUUID().toString();
        save(new UserAddQuestPackEvent(userId, packId, userId));
        return packId;
    }

    public void addQuestPack(String userId, String packId, String inviterUserId) {
        save(new UserAddQuestPackEvent(userId, packId, inviterUserId));
    }
}
