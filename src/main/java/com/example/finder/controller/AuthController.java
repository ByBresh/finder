package com.example.finder.controller;

import com.example.finder.model.User;
import com.example.finder.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private UserService userService;

    @PostMapping(value= "/register", consumes = "multipart/form-data")
    public ResponseEntity<?> register(
            @RequestParam("name") String name,
            @RequestParam("email") String email,
            @RequestParam("password") String password,
            @RequestParam("profilePicture") MultipartFile profilePicture
            ) {
        try {
            User newUser = userService.registerUser(new User(name, email, password, profilePicture.getBytes()));
            return ResponseEntity.ok("Usuario registrado " + newUser.getName());
        } catch (RuntimeException | IOException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

}
