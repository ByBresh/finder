package com.example.finder.service;

import com.example.finder.dao.UserRepository;
import com.example.finder.model.User;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private UserRepository repository;

    public UserService(UserRepository userRepository) {
        this.repository = userRepository;
    }

    public User createUser(User user) {
        if (repository.existsByEmail(user.getEmail())) {
            return null;
        }
        return repository.save(user);
    }

}
