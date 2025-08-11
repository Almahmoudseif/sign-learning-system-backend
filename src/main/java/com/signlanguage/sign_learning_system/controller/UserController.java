package com.signlanguage.sign_learning_system.controller;

import com.signlanguage.sign_learning_system.payload.LoginRequest;
import com.signlanguage.sign_learning_system.DTO.LoginResponse;
import com.signlanguage.sign_learning_system.DTO.RegisterTeacherRequest;
import com.signlanguage.sign_learning_system.DTO.TeacherProfileResponse;
import com.signlanguage.sign_learning_system.enums.LessonLevel;
import com.signlanguage.sign_learning_system.model.User;
import com.signlanguage.sign_learning_system.repository.UserRepository;
import com.signlanguage.sign_learning_system.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostMapping("/register")
    public User registerUser(@RequestBody User user) {
        return userService.registerUser(user);
    }

    @PostMapping("/register-teacher")
    public User registerTeacher(@RequestBody RegisterTeacherRequest request) {
        return userService.registerTeacher(request);
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest) {
        User user = userRepository.findByRegistrationNumber(loginRequest.getRegistrationNumber())
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        if (!passwordEncoder.matches(loginRequest.getPassword(), user.getPassword())) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid credentials");
        }

        String fullName = (user.getFullName() != null && !user.getFullName().isEmpty())
                ? user.getFullName()
                : "User";

        LoginResponse response = new LoginResponse(
                user.getId(),
                user.getRegistrationNumber(),
                fullName,
                user.getRole().toString(),
                user.getLevel() != null ? user.getLevel().name() : null  // hii ni ile 5th param
        );


        return ResponseEntity.ok(response);
    }



    @GetMapping("/teacher/{id}")
    public ResponseEntity<?> getTeacherProfile(@PathVariable Long id) {
        try {
            TeacherProfileResponse profile = userService.getTeacherProfileById(id);
            return ResponseEntity.ok(profile);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
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
            @RequestParam(defaultValue = "BEGINNER") String defaultLevelStr) {

        LessonLevel defaultLevel;
        try {
            defaultLevel = LessonLevel.valueOf(defaultLevelStr.toUpperCase());
        } catch (IllegalArgumentException e) {
            defaultLevel = LessonLevel.BEGINNER;
        }

        List<User> updated = userService.assignLevelToStudentsWithoutLevel(defaultLevel);
        return ResponseEntity.ok(updated);
    }
}
