package com.signlanguage.sign_learning_system.controller;

import com.signlanguage.sign_learning_system.model.User;
import com.signlanguage.sign_learning_system.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "http://localhost:3000") // adjust IP/port as needed
@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/register")
    public User registerUser(@RequestBody User user) {
        return userService.registerUser(user);
    }

    @PostMapping("/login")
    public User loginUser(@RequestParam String registrationNumber, @RequestParam String password) {
        return userService.loginUser(registrationNumber, password);
    }

    @GetMapping
    public List<User> getAllUsers() {
        return userService.getAllUsers();
    }

    @GetMapping("/role/{role}")
    public List<User> getUsersByRole(@PathVariable String role) {
        return userService.getUsersByRole(role);
    }

    @GetMapping("/students")
    public List<User> getAllStudents() {
        return userService.getAllStudents();
    }

    @GetMapping("/{id}")
    public User getUserById(@PathVariable Long id) {
        return userService.getUserById(id);
    }

    @DeleteMapping("/{id}")
    public String deleteUser(@PathVariable Long id) {
        userService.deleteUserById(id);
        return "User deleted successfully";
    }

    @PutMapping("/fix-students-level")
    public ResponseEntity<List<User>> fixStudentLevels(
            @RequestParam(defaultValue = "Beginner") String defaultLevel) {

        List<User> updated = userService.assignLevelToStudentsWithoutLevel(defaultLevel);
        return ResponseEntity.ok(updated);
    }
}
