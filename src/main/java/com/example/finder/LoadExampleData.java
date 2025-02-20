package com.example.finder;

import com.example.finder.model.User;
import com.example.finder.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

@Component
public class LoadExampleData {

    private final UserService userService;

    @Autowired
    public LoadExampleData(UserService userService) {
        this.userService = userService;
    }

    public void run() {

        List<User> users = List.of();
        try {
            users = List.of(
                new User("Alice", "alice@example.com", "hola", "1234", Files.readAllBytes(Path.of("src/main/resources/demo/images/alice.jpg"))),
                new User("Bob", "bob@example.com", "hola", "1234", Files.readAllBytes(Path.of("src/main/resources/demo/images/bob.jpg"))),
                new User("Charlie", "charlie@example.com", "hola", "1234", Files.readAllBytes(Path.of("src/main/resources/demo/images/charlie.jpg"))),
                new User("Dana", "dana@example.com", "hola", "1234", Files.readAllBytes(Path.of("src/main/resources/demo/images/dana.jpg"))),
                new User("Eve", "eve@example.com", "hola", "1234", Files.readAllBytes(Path.of("src/main/resources/demo/images/eve.jpg")))
            );
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        for (User user: users) {
            try {
                userService.registerUser(user);
            } catch (RuntimeException e) {
                e.printStackTrace();
            }
        }
    }
}
