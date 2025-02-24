package com.example.finder.controller;

import com.example.finder.model.User;
import com.example.finder.service.LikeService;
import com.example.finder.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/api")
public class ApiController {

    @Autowired
    private LikeService likeService;

    @Autowired
    private UserService userService;

    @PostMapping("/like")
    public ResponseEntity<?> like(@RequestParam Integer id, Authentication authentication) {
        String response = likeService.like(userService.getUserByUsername(authentication.getName()).getEmail(), id).orElse("Usuario liked");
        return ResponseEntity.ok(response);
    }

    @PostMapping("/dislike")
    public ResponseEntity<?> dislike(@RequestParam Integer id, Authentication authentication) {
        likeService.dislike(userService.getUserByUsername(authentication.getName()).getEmail(), id);
        return ResponseEntity.ok("Usuario disliked");
    }

    @PostMapping(value= "/edit-profile", consumes = "multipart/form-data")
    public ResponseEntity<?> editProfile(
            @RequestParam("name") String name,
            @RequestParam("bio") String bio,
            @RequestParam(value = "profilePicture", required = false) MultipartFile profilePicture,
            Authentication authentication
    ) {
        try {
            User user = userService.getUserByUsername(authentication.getName());
            if (profilePicture == null)
                user = userService.editUser(user, name, bio, user.getProfilePicture());
            else
                user = userService.editUser(user, name, bio, profilePicture.getBytes());
            return ResponseEntity.ok("Perfil actualizado.");
        } catch (RuntimeException | IOException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping(value = "/send-message", consumes = "multipart/form-data")
    public ResponseEntity<?> sendMessage(
            @RequestParam Integer id,
            @RequestParam String messageText,
            Authentication authentication
    ) {
        try {
            likeService.sendMessage(userService.getUserByUsername(authentication.getName()), id, messageText);
            return ResponseEntity.ok("Mensaje enviado.");
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/reset-pending-match")
    public ResponseEntity<?> resetPendingMatch(Authentication authentication) {
        userService.resetPendingMatch(userService.getUserByUsername(authentication.getName()));
        return ResponseEntity.ok("Match pendiente reseteado.");
    }

}
