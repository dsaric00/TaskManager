package com.example.taskmanager.services;

import com.example.taskmanager.models.User;
import com.example.taskmanager.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public User getUserByUsername(String username) {
        return userRepository.findByEmail(username);
    }
    public User getCurrentUser(String username){
        return userRepository.findByEmail(username);
    }

    public void updateUser(User user) {
        userRepository.save(user);
    }

    public void updatePassword(User user, String newPassword) {
        user.setLozinka(passwordEncoder.encode(newPassword));
        userRepository.save(user);
    }
}
