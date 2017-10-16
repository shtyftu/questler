package net.shtyftu.ubiquode.controller;

import net.shtyftu.ubiquode.service.QuestService;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author shtyftu
 */
public class UserQuestController {

    @Autowired
    private QuestService questService;

    public String getList() {
        return questService.getAll().toString();
    }

    public String lockQuest(String questId, String userId) {
        boolean result = questService.lock(questId, userId);
        return result ? "OK" : "FAIL";
    }


}
