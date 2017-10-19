package net.shtyftu.ubiquode.controller;

import net.shtyftu.ubiquode.service.QuestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author shtyftu
 */
@Controller
public class UserQuestController {

    @Value("${application.message:Hello World}")
    private String message = "Hello World";

    @Autowired
    private QuestService questService;

    @RequestMapping("/quest/list")
    public String getList() {
        return questService.getAll().toString();
    }

    @RequestMapping("/quest/lock")
    public String lockQuest(
            @RequestParam(name = "questId") String questId,
            @RequestParam(name = "userId") String userId) {
        boolean result = questService.lock(questId, userId);
        return result ? "OK" : "FAIL";
    }

    @RequestMapping("/foo")
    public String welcome(Model model) {
        model.addAttribute("message", this.message);
        return "welcome";
    }
//
//    private static final String template = "Hello, %s!";
//    private final AtomicLong counter = new AtomicLong();
//
//    @RequestMapping("/greeting")
//    public Greeting greeting(@RequestParam(value="name", defaultValue="World") String name) {
//        return new Greeting(counter.incrementAndGet(),
//                String.format(template, name));
//    }


}
