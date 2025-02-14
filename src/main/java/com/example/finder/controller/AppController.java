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
@RequestMapping("/app")
public class AppController {

    @Autowired
    private UserService userService;

    @GetMapping("/likes")
    public String likes(Model model, Authentication authentication) {
        Set<User> likedUsers = userService.getUserByUsername(authentication.getName()).getLikedUsers();
        model.addAttribute("likedUsers", likedUsers);
        return "likedUsers";
    }

    @GetMapping("/matches")
    public String matches(Model model, Authentication authentication, @RequestParam(required = false) String selectedUserId) {
        User user = userService.getUserByUsername(authentication.getName());
        Set<UserMatch> matches = userService.getUserByUsername(authentication.getName()).getAllMatches();
        UserMatch selectedMatch = null;
        if (selectedUserId != null) {
            List<UserMatch> selectedMatchList = matches.stream().filter(match -> selectedUserId.equals(String.valueOf(match.getOtherUser(user).getId()))).toList();
            selectedMatch = selectedMatchList.isEmpty() ? null : selectedMatchList.getFirst();
        }
        model.addAttribute("matches", matches);
        model.addAttribute("selectedMatch", selectedMatch);
        return "matches";
    }

}
