package net.shtyftu.ubiquode.controller;

import net.shtyftu.ubiquode.model.persist.simple.Account;
import net.shtyftu.ubiquode.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author shtyftu
 */
@Controller
public class AccountController {

    @Autowired
    private UserQuestController userQuestController;

    @Autowired
    private AccountService service;

    @RequestMapping("/")
    public String loginPage() {
        return "loginPage";
    }

    @RequestMapping("/register")
    public String registerPage() {
        return "registerPage";
    }

    @RequestMapping("/login")
    public String login(
            @RequestParam(name = "login") String login,
            @RequestParam(name = "password") String password) {
        return service.validateUser(new Account(login, password, login))
                ? userQuestController.getList()
                : loginPage();
    }

    @RequestMapping("/register/user")
    public String register(
            @RequestParam(name = "login") String login,
            @RequestParam(name = "password") String password) {
        return service.register(new Account(login, password, login))
                ? login(login, password)
                : registerPage();
    }

}
