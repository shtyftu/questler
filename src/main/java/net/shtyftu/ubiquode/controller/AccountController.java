package net.shtyftu.ubiquode.controller;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import net.shtyftu.ubiquode.model.persist.simple.Account;
import net.shtyftu.ubiquode.service.AccountService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author shtyftu
 */
@Controller
public class AccountController {

//    @Autowired
//    private AccountService service;

//    @RequestMapping("/")
//    public String loginPage() {
//        return "loginPage";
//    }

//    @RequestMapping("/register")
//    public String registerPage() {
//        return "registerPage";
//    }

    @RequestMapping("/login")
    public String login(
            @RequestParam(name = "login", required = false) String login,
            @RequestParam(name = "password", required = false) String password,
            HttpServletRequest request) {
        try {
            request.login(login, password);
            return "redirect:/quest/list";
        } catch (ServletException e) {
            e.printStackTrace();
        }
        return "redirect:login";
    }

    @RequestMapping("/logout")
    public String logout(HttpServletRequest request) {
        try {
            request.logout();
        } catch (ServletException e) {
            e.printStackTrace();
        }
        return "redirect:login";
    }

//    @RequestMapping("/register")
//    public String register(
//            @RequestParam(name = "login", required = false) String login,
//            @RequestParam(name = "password", required = false) String password,
//            HttpServletRequest request) {
//        if (StringUtils.isBlank(login) || StringUtils.isBlank(password)) {
//            return "register";
//        }
//        if (!service.register(new Account(login, password, login))) {
//            return "register";
//        }
//        return login(login, password, request);
//    }

}
