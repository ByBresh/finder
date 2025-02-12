package com.example.finder.controller;

import com.example.finder.model.User;
import com.example.finder.service.LikeService;
import com.example.finder.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/likes")
public class LikeController {

    @Autowired
    private LikeService likeService;

    @Autowired
    private UserService userService;

    // TODO FIx this
    @GetMapping("/like")
    public ResponseEntity<?> like(@RequestParam Long id, Authentication authentication) {
        likeService.like(userService.getUserByUsername(authentication.getName()).getEmail(), id);
        return ResponseEntity.ok("Usuario liked");
    }

}
