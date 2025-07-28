package com.signlanguage.sign_learning_system.service;

import com.signlanguage.sign_learning_system.model.User;
import com.signlanguage.sign_learning_system.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public User registerUser(User user) {
        if (user.getLevel() == null || user.getLevel().trim().isEmpty()) {
            user.setLevel("Beginner");  // Set default level here
        }
        user.setRegistrationNumber(generateRegistrationNumber());
        return userRepository.save(user);
    }

    public User loginUser(String registrationNumber, String password) {
        User user = userRepository.findByRegistrationNumber(registrationNumber);
        if (user != null && user.getPassword().equals(password)) {
            return user;
        }
        return null;
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public List<User> getUsersByRole(String role) {
        return userRepository.findByRole(role);
    }

    public List<User> getAllStudents() {
        return userRepository.findByRole("student");
    }

    public User getUserById(Long id) {
        return userRepository.findById(id).orElse(null);
    }

    public void deleteUserById(Long id) {
        userRepository.deleteById(id);
    }

    public User getUserByRegistrationNumber(String regNumber) {
        return userRepository.findByRegistrationNumber(regNumber);
    }

    public User updateUser(Long id, User updatedUser) {
        User existingUser = getUserById(id);
        if (existingUser != null) {
            existingUser.setFullName(updatedUser.getFullName());
            existingUser.setEmail(updatedUser.getEmail());
            existingUser.setPassword(updatedUser.getPassword());
            existingUser.setRole(updatedUser.getRole());
            existingUser.setLevel(updatedUser.getLevel());
            return userRepository.save(existingUser);
        }
        return null;
    }

    public List<User> assignLevelToStudentsWithoutLevel(String defaultLevel) {
        List<User> students = userRepository.findByRole("student");
        List<User> updated = new ArrayList<>();

        for (User user : students) {
            if (user.getLevel() == null || user.getLevel().trim().isEmpty()) {
                user.setLevel(defaultLevel);
                updated.add(userRepository.save(user));
            }
        }

        return updated;
    }

    private String generateRegistrationNumber() {
        Random random = new Random();
        String reg;
        do {
            reg = "SL-" + (1000 + random.nextInt(9000));
        } while (userRepository.findByRegistrationNumber(reg) != null);
        return reg;
    }
}
