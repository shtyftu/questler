package net.shtyftu.ubiquode.controller;

import com.google.common.collect.ImmutableMap;
import java.util.List;
import java.util.stream.Collectors;
import javax.servlet.http.HttpServletRequest;
import net.shtyftu.ubiquode.model.web.QuestModel;
import net.shtyftu.ubiquode.service.QuestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

/**
 * @author shtyftu
 */
@Controller
@RequestMapping("/quest")
public class UserQuestController {

//    @Value("${application.message:Hello World}")
//    private String hello = "Hello World";


    private final QuestService questService;

    @Autowired
    public UserQuestController(QuestService questService) {
        this.questService = questService;
    }

    @RequestMapping(path = "/list", method = RequestMethod.GET)
    public ModelAndView getList(HttpServletRequest request) {
        final String userId = null;
        final List<QuestModel> questList = questService.getAll().stream()
                .map((questState) -> new QuestModel(questState, userId))
                .sorted()
                .collect(Collectors.toList());
        return new ModelAndView("quest/list", ImmutableMap.of("questList", questList));
    }

    //todo make it post
    @RequestMapping("/enable")
    public ModelAndView lockQuest(
            @RequestParam(name = "questId") String questId,
            HttpServletRequest request) {
        boolean result = questService.enable(questId);
        return getList(request);
    }

    @RequestMapping("/lock")
    public ModelAndView lockQuest(
            @RequestParam(name = "questId") String questId,
            @RequestParam(name = "userId") String userId,
            HttpServletRequest request) {
        boolean result = questService.lock(questId, userId);
        return getList(request);
    }

    @RequestMapping("/complete")
    public ModelAndView completeQuest(
            @RequestParam(name = "questId") String questId,
            @RequestParam(name = "userId") String userId,
            HttpServletRequest request) {
        boolean result = questService.complete(questId, userId);
        return getList(request);
    }

//    @RequestMapping("/foo")
//    public String welcome(AModel model) {
//        model.addAttribute("message", this.hello);
//        return "welcome";
//    }
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
