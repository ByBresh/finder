package com.example.finder.controller;

import com.example.finder.model.User;
import com.example.finder.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/test")
public class TestController {

    @Autowired
    private UserService userService;

    @GetMapping("")
    public String test(Model model, Authentication authentication) {
        String username = authentication.getName();
        model.addAttribute("username", username + userService.getUserByUsername(username).getName());

        return "test";
    }

}
