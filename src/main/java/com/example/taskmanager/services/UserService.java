package com.example.taskmanager.services;

import com.example.taskmanager.models.User;
import com.example.taskmanager.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public User findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    public void updateUser(User updateUser){
        if (updateUser.getId() == null) {
            throw new IllegalArgumentException("The given id must not be null");
        }
        User existingUser = userRepository.findById(updateUser.getId())
                .orElseThrow(() -> new IllegalArgumentException("Invalid user ID"));
        existingUser.setIme(updateUser.getIme());
        existingUser.setPrezime(updateUser.getPrezime());
        existingUser.setEmail(updateUser.getEmail());
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        String lozinka = encoder.encode(existingUser.getLozinka());
        existingUser.setLozinka(lozinka); // Provjerite ako lozinka treba biti enkriptirana
        existingUser.setPotvrdaLozinke(lozinka);
        userRepository.save(existingUser);
    }

}
