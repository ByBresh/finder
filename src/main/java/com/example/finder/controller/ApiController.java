package com.example.finder.controller;

import com.example.finder.service.LikeService;
import com.example.finder.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class ApiController {

    @Autowired
    private LikeService likeService;

    @Autowired
    private UserService userService;

    @GetMapping("/like")
    public ResponseEntity<?> like(@RequestParam Integer id, Authentication authentication) {
        String response = likeService.like(userService.getUserByUsername(authentication.getName()).getEmail(), id).orElse("Usuario liked");
        return ResponseEntity.ok(response);
    }

}
