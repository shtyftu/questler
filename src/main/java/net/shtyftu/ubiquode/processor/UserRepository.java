package net.shtyftu.ubiquode.processor;

import net.shtyftu.ubiquode.dao.list.event.EventDao;
import net.shtyftu.ubiquode.model.persist.composite.event.user.UserAddQuestPackEvent;
import net.shtyftu.ubiquode.model.persist.composite.event.user.UserEvent;
import net.shtyftu.ubiquode.model.persist.composite.event.user.UserQuestCompleteEvent;
import net.shtyftu.ubiquode.model.persist.composite.event.user.UserQuestLockEvent;
import net.shtyftu.ubiquode.model.projection.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author shtyftu
 */
@Component
public class UserRepository extends ARepository<User, UserEvent> {


    @Autowired
    public UserRepository(EventDao<UserEvent> eventDao) {
        super(eventDao);
    }

    @Override
    protected User createNew(String key) {
        return new User(key);
    }

    public void lock(String userId, String questId, long eventTime) {
        save(new UserQuestLockEvent(userId, questId, eventTime));
    }

    public void complete(String userId, int scores, long eventTime) {
        save(new UserQuestCompleteEvent(userId, scores, eventTime));
    }

    public void addNewQuestPack(String userId, String packId, long eventTime) {
        save(new UserAddQuestPackEvent(userId, packId, userId, eventTime));
    }

    public void addQuestPack(String userId, String packId, String inviterUserId, long eventTime) {
        save(new UserAddQuestPackEvent(userId, packId, inviterUserId, eventTime));
    }
}
