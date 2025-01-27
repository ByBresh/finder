package com.example.finder;

import com.example.finder.model.User;
import com.example.finder.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/createUser")
    public ResponseEntity<User> createUser(@RequestBody User user) {
        HttpStatus status = HttpStatus.CREATED;
        User createdUser = userService.createUser(user);
        if (createdUser == null) {
            status = HttpStatus.CONFLICT;
        }
        return new ResponseEntity<>(createdUser, status);
    }

}
