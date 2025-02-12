package com.example.finder.service;

import com.example.finder.dao.UserRepository;
import com.example.finder.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class LikeService {

    @Autowired
    private UserRepository userRepository;

    @Transactional
    public void like(String username, Long id) {
        User user = userRepository.findByEmail(username).orElseThrow(() -> new RuntimeException("Usuario no encontrado: " + username));
        User likedUser = userRepository.findById(id).orElseThrow(() -> new RuntimeException("Usuario no encontrado: " + id));
        user.getDislikedUsers().remove(likedUser);
        user.getLikedUsers().add(likedUser);
        checkMatch(user, likedUser);
        userRepository.save(user);
        userRepository.save(likedUser);
    }

    @Transactional
    public void dislike(String username, Long id) {
        User user = userRepository.findByEmail(username).orElseThrow(() -> new RuntimeException("Usuario no encontrado: " + username));
        User dislikedUser = userRepository.findById(id).orElseThrow(() -> new RuntimeException("Usuario no encontrado: " + id));
        checkMatch(user, dislikedUser);
        user.getLikedUsers().remove(dislikedUser);
        user.getDislikedUsers().add(dislikedUser);
        userRepository.save(user);
        userRepository.save(dislikedUser);
    }

    private void checkMatch(User user, User likedUser) {
        if (user.getLikedUsers().contains(likedUser) && likedUser.getLikedUsers().contains(user)) {
            user.getMatchedUsers().add(likedUser);
            likedUser.getMatchedUsers().add(user);
        } else {
            user.getMatchedUsers().remove(likedUser);
            likedUser.getMatchedUsers().remove(user);
        }
    }

}
