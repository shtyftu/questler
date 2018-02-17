package net.shtyftu.ubiquode.model.view;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.SimpleTimeZone;
import java.util.concurrent.TimeUnit;
import net.shtyftu.ubiquode.model.persist.composite.event.quest.QuestEvent;

/**
 * @author shtyftu
 */
public class QuestEventView {

    private final String time;
    private final String userId;
    private final String eventType;
    private final String questName;

    public QuestEventView(QuestEvent event, String name, String user) {
        final SimpleDateFormat formatter = new SimpleDateFormat("yyy-MM-dd HH:mm");
        formatter.setTimeZone(new SimpleTimeZone(Math.toIntExact(TimeUnit.HOURS.toMillis(3)), "timeZone"));
        time = formatter.format(new Date(event.getTime()));
        userId = user;
        eventType = event.getViewName();
        questName = name;
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
