package net.shtyftu.ubiquode.processor;

import net.shtyftu.ubiquode.dao.composite.event.EventDao;
import net.shtyftu.ubiquode.dao.simple.QuestProtoDao;
import net.shtyftu.ubiquode.model.persist.composite.event.user.UserEvent;
import net.shtyftu.ubiquode.model.persist.composite.event.user.UserQuestCompleteEvent;
import net.shtyftu.ubiquode.model.persist.composite.event.user.UserQuestLockEvent;
import net.shtyftu.ubiquode.model.persist.simple.QuestProto;
import net.shtyftu.ubiquode.model.persist.simple.User;
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

    public void complete(String userId, String questId) {
        final QuestProto questProto = questProtoDao.getByKey(questId);
        save(new UserQuestCompleteEvent(userId, questProto.getScores()));
    }
}
