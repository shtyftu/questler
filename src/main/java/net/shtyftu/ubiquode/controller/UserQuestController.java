package net.shtyftu.ubiquode.controller;

import com.google.common.collect.ImmutableMap;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import net.shtyftu.ubiquode.model.projection.QuestState;
import net.shtyftu.ubiquode.service.QuestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

/**
 * @author shtyftu
 */
@Controller
@RequestMapping("/quest")
public class UserQuestController {

    @Value("${application.message:Hello World}")
    private String hello = "Hello World";

    @Autowired
    private QuestService questService;

    @RequestMapping("/list")
    public ModelAndView getList(HttpServletRequest request) {
        final List<QuestState> questStateList = questService.getAll();
        return new ModelAndView("quest/list", ImmutableMap.of("message", "zag-zag"));
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
        model.addAttribute("message", this.hello);
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
