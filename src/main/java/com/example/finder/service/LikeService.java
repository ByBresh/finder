package com.example.finder.service;

import com.example.finder.dao.UserMatchRepository;
import com.example.finder.dao.UserRepository;
import com.example.finder.model.MatchMessage;
import com.example.finder.model.User;
import com.example.finder.model.UserMatch;
import com.example.finder.model.UserMatchId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class LikeService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserMatchRepository userMatchRepository;

    @Transactional
    public synchronized Optional<String> like(String username, Integer id) {
        Optional<String> response = Optional.empty();
        User user = userRepository.findByEmail(username).orElseThrow(() -> new RuntimeException("Usuario no encontrado: " + username));
        User likedUser = userRepository.findById(id).orElseThrow(() -> new RuntimeException("Usuario no encontrado: " + id));
        user.getDislikedUsers().remove(likedUser);
        user.getLikedUsers().add(likedUser);
        if (user.getLikedUsers().contains(likedUser) && user.getLikedByUsers().contains(likedUser)) {
            UserMatch match = new UserMatch(user, likedUser);
            userMatchRepository.save(match);
            user.setPendingMatch(true);
            likedUser.setPendingMatch(true);
            response = Optional.of("Match!");
        }
        userRepository.save(user);
        userRepository.save(likedUser);
        return response;
    }

    @Transactional
    public void dislike(String username, Integer id) {
        User user = userRepository.findByEmail(username).orElseThrow(() -> new RuntimeException("Usuario no encontrado: " + username));
        User dislikedUser = userRepository.findById(id).orElseThrow(() -> new RuntimeException("Usuario no encontrado: " + id));
        user.getLikedUsers().remove(dislikedUser);
        user.getDislikedUsers().add(dislikedUser);
        userRepository.save(user);
        userRepository.save(dislikedUser);
    }

    @Transactional
    public void sendMessage(User user, Integer otherUserId, String messageText) {
        User otherUser = userRepository.findById(otherUserId).orElse(null);
        if (otherUser == null)
            throw new RuntimeException("El usuario no existe.");
        if (!user.getAllMatchedUsers().contains(otherUser))
            throw new RuntimeException("No puedes enviar mensajes a usuarios con los que no tienes match.");
        UserMatch match = userMatchRepository.findById(new UserMatchId(user.getId(), otherUser.getId()))
                .orElseThrow(() -> new RuntimeException("Match no encontrado."));
        match.getMessages().add(new MatchMessage(match, user, messageText));
    }

}
