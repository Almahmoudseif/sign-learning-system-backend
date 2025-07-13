package com.signlanguage.sign_learning_system.controller;

import com.signlanguage.sign_learning_system.model.User;
import com.signlanguage.sign_learning_system.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
@CrossOrigin(origins = "*")
public class UserController {

    @Autowired
    private UserService userService;

    // Sajili user mpya
    @PostMapping("/register")
    public User registerUser(@RequestBody User user) {
        return userService.registerUser(user);
    }

    // Login kwa registrationNumber na password
    @PostMapping("/login")
    public User loginUser(@RequestParam String registrationNumber, @RequestParam String password) {
        return userService.loginUser(registrationNumber, password);
    }

    // Rudisha users wote
    @GetMapping
    public List<User> getAllUsers() {
        return userService.getAllUsers();
    }

    // Rudisha user kwa ID
    @GetMapping("/{id}")
    public User getUserById(@PathVariable Long id) {
        return userService.getUserById(id);
    }

    // Rudisha user kwa registration number
    @GetMapping("/by-registration/{regNumber}")
    public User getUserByRegistrationNumber(@PathVariable String regNumber) {
        return userService.getUserByRegistrationNumber(regNumber);
    }

    // Futa user kwa ID
    @DeleteMapping("/{id}")
    public String deleteUser(@PathVariable Long id) {
        userService.deleteUserById(id);
        return "User deleted successfully";
    }

    // Rekebisha user kwa ID
    @PutMapping("/{id}")
    public User updateUser(@PathVariable Long id, @RequestBody User updatedUser) {
        User existingUser = userService.getUserById(id);
        if (existingUser != null) {
            existingUser.setFullName(updatedUser.getFullName());
            existingUser.setEmail(updatedUser.getEmail());
            existingUser.setPassword(updatedUser.getPassword());
            existingUser.setRegistrationNumber(updatedUser.getRegistrationNumber());
            existingUser.setRole(updatedUser.getRole());
            return userService.registerUser(existingUser);
        }
        return null;
    }
}
