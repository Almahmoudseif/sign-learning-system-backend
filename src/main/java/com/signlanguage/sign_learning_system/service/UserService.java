package com.signlanguage.sign_learning_system.service;

import com.signlanguage.sign_learning_system.model.User;
import com.signlanguage.sign_learning_system.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    // Sajili user mpya au update kama ipo
    public User registerUser(User user) {
        // Hapa unaweza kuongeza validation kama email au reg number imetumika
        return userRepository.save(user);
    }

    // Login: Rudisha user kama registrationNumber na password vinaendana
    public User loginUser(String registrationNumber, String password) {
        User user = userRepository.findByRegistrationNumber(registrationNumber);
        if (user != null && user.getPassword().equals(password)) {
            return user;
        }
        return null;
    }

    // Rudisha users wote
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    // Rudisha user kwa ID
    public User getUserById(Long id) {
        return userRepository.findById(id).orElse(null);
    }

    // Futa user kwa ID
    public void deleteUserById(Long id) {
        userRepository.deleteById(id);
    }

    // Rudisha user kwa registration number
    public User getUserByRegistrationNumber(String regNumber) {
        return userRepository.findByRegistrationNumber(regNumber);
    }
}
