package com.example.finder.service;

import com.example.finder.dao.UserRepository;
import com.example.finder.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
public class UserService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public User registerUser(User user) throws RuntimeException {
        if (userRepository.existsByEmail(user.getEmail()))
            throw new RuntimeException("Este email ya está en uso.");
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }

    public User editUser(User user, String name, String bio, MultipartFile profilePicture) throws RuntimeException, IOException {
        if (!userRepository.existsByEmail(user.getEmail()))
            throw new RuntimeException("El usuario no existe");
        user.setName(name);
        user.setBio(bio);
        user.setProfilePicture(profilePicture.getBytes());
        return userRepository.save(user);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(username)
                .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado: " + username));

        return org.springframework.security.core.userdetails.User.builder()
                .username(user.getEmail())
                .password(user.getPassword())
                .roles("USER")
                .build();
    }

    public User getUserByUsername(String username) {
        return userRepository.findByEmail(username)
                .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado: " + username));
    }

    public User getUserById(Integer id) {
        return userRepository.findById(id)
                .orElse(null);
    }
    
}
