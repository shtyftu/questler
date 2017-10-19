package net.shtyftu.ubiquode.service;

import java.util.concurrent.TimeUnit;

/**
 * @author shtyftu
 */
public class ConfigService {

    public static final long QUEST_LOCK_TIME = TimeUnit.HOURS.toMillis(2);
    public static final long UNCOMPLETED_LOCK_PENALTY_TIME = TimeUnit.HOURS.toMillis(8);
    public static final long PANIC_TIME_BEFORE_DEADLINE = TimeUnit.HOURS.toMillis(12);


}
