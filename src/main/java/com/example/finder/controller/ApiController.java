package com.example.finder.controller;

import com.example.finder.model.User;
import com.example.finder.service.LikeService;
import com.example.finder.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

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

    @PostMapping(value= "/edit-profile", consumes = "multipart/form-data")
    public ResponseEntity<?> editProfile(
            @RequestParam("name") String name,
            @RequestParam("bio") String bio,
            @RequestParam("profilePicture") MultipartFile profilePicture,
            Authentication authentication
    ) {
        try {
            User user = userService.getUserByUsername(authentication.getName());
            userService.editUser(user, name, bio, profilePicture);
            return ResponseEntity.ok("Perfil actualizado.");
        } catch (RuntimeException | IOException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

}
