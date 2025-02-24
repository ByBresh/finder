package com.example.finder.controller;

import com.example.finder.model.User;
import com.example.finder.model.UserMatch;
import com.example.finder.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Set;

@Controller
@RequestMapping("")
public class AppController {

    @Autowired
    private UserService userService;

    @GetMapping("/")
    public String index (Model model, Authentication authentication) {
        return "index";


    }

    @GetMapping("/register")
    public String register(@RequestParam(value = "step2", required = false) String step2, Model model, Authentication authentication) {

        if (step2 != null) {
            return "register-part2";
        } else {
            return "register-part1";
        }
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/swipe")
    public String swipe(Model model, Authentication authentication) {
        User user = userService.getRandomUser(userService.getUserByUsername(authentication.getName()));
        model.addAttribute("user", user);
        User currentUser = userService.getUserByUsername(authentication.getName());
        model.addAttribute("currentUser", currentUser);
        return "swipe";
    }

    @GetMapping("/matches")
    public String matches(@RequestParam(value = "id", required = false) final Integer id, Model model, Authentication authentication) {
        User user = userService.getUserByUsername(authentication.getName());
        Set<UserMatch> matches = user.getAllMatches().isEmpty() ? null : user.getAllMatches();
        UserMatch selectedMatch;
        model.addAttribute("user", user);
        model.addAttribute("matches", matches);
        if (matches == null) {
            selectedMatch = null;
        } else if (id == null) {
            selectedMatch = matches.iterator().next();
        } else if (matches.contains(new UserMatch(user, new User(id)))) {
            selectedMatch = matches.stream().filter(match -> match.getOtherUser(user).getId().equals(id)).findFirst().get();
        } else {
            selectedMatch = matches.iterator().next();
        }
        model.addAttribute("selectedMatch", selectedMatch);
        return "matches";
    }


    @GetMapping("/profile")
    public String profile(@RequestParam(value = "edit", required = false) String edit, @RequestParam(value = "id", required = false) Integer id, Model model, Authentication authentication) {
        User user;
        if (id != null && userService.getUserById(id) != null) {
            user = userService.getUserById(id);
            model.addAttribute("otherUser", true);
            model.addAttribute("user", user);
            return "profile";
        } else {
            user = userService.getUserByUsername(authentication.getName());
            model.addAttribute("otherUser", false);
            model.addAttribute("user", user);
            if (edit != null) {
                return "edit-profile";
            } else {
                return "profile";
            }
        }
    }

}
