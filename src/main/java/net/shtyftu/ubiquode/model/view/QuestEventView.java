package net.shtyftu.ubiquode.model.view;

import java.sql.Date;
import java.text.SimpleDateFormat;
import net.shtyftu.ubiquode.model.persist.composite.event.quest.QuestEvent;

/**
 * @author shtyftu
 */
public class QuestEventView {

    private final String time;
    private final String userId;
    private final String eventType;
    private final String questName;

    public QuestEventView(QuestEvent event, String name, String userId) {
        this(
                new SimpleDateFormat("yyy-MM-dd HH:mm").format(new Date(event.getTime())),
                userId,
                event.getViewName(),
                name
        );
    }

    private QuestEventView(String time, String userId, String eventType, String questName) {
        this.time = time;
        this.userId = userId;
        this.eventType = eventType;
        this.questName = questName;
    }

    public String getTime() {
        return time;
    }

    public String getUserId() {
        return userId;
    }

    public String getEventType() {
        return eventType;
    }

    public String getQuestName() {
        return questName;
    }
}
