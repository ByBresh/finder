package com.example.finder.controller;

import com.example.finder.model.User;
import com.example.finder.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/app")
public class AppController {

    @Autowired
    private UserService userService;

    @GetMapping("/likes")
    public String matches(Model model, Authentication authentication) {
        List<User> likedUsers = userService.getUserByUsername(authentication.getName()).getLikedUsers();
        model.addAttribute("likedUsers", likedUsers);
        return "likedUsers";
    }

}
