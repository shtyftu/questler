package net.shtyftu.ubiquode.dao.composite.event;

import net.shtyftu.ubiquode.model.persist.composite.event.quest.QuestEvent;
import org.springframework.stereotype.Component;

/**
 * @author shtyftu
 */
@Component
public class QuestEventDao extends HashMapEventDao<QuestEvent, String> {

}
