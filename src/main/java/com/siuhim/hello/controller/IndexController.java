package com.siuhim.hello.controller;

import com.siuhim.hello.dto.UserDto;
import com.siuhim.hello.exception.ValidationException;
import com.siuhim.hello.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.security.Principal;

@Controller
public class IndexController {

    @Autowired
    private UserService userService;

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String index() {
        return "index";
    }

    @RequestMapping(value = "/admin", method = RequestMethod.GET)
    public String admin() {
        return "admin";
    }

    @RequestMapping(value = "/user", method = RequestMethod.GET)
    public String user(Principal principal, Model model) {
        String name = userService.retrieveName(principal.getName());
        model.addAttribute("name", name);
        return "user";
    }

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String login() {
        return "login";
    }

    @RequestMapping(value = "/login_failed", method = RequestMethod.GET)
    public String login_failed() {
        return "login_failed";
    }

    @RequestMapping(value = "/register", method = RequestMethod.GET)
    public String register(Model model) {
        model.addAttribute("user", new UserDto());
        return "register";
    }

    @RequestMapping(value = "/perform_register", method = RequestMethod.POST)
    public String perform_register(@ModelAttribute("user") UserDto userDto, Model model) {
        try {
            userService.register(userDto);
        } catch (ValidationException e) {
            model.addAttribute("msgList", e.getMessages());
            return "register_failed";
        }
        return "register_success";
    }
}
