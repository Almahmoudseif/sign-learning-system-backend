package com.signlanguage.sign_learning_system.controller;

import com.signlanguage.sign_learning_system.model.User;
import com.signlanguage.sign_learning_system.payload.LoginRequest;
import com.signlanguage.sign_learning_system.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private UserRepository userRepository;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest) {
        Optional<User> optionalUser = userRepository.findByRegistrationNumber(loginRequest.getRegistrationNumber());

        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            if (user.getFullName().equalsIgnoreCase(loginRequest.getFullName())) {
                Map<String, Object> response = new HashMap<>();
                response.put("registrationNumber", user.getRegistrationNumber());
                response.put("fullName", user.getFullName());
                response.put("role", user.getRole());
                response.put("id", user.getId());
                return ResponseEntity.ok(response);
            } else {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Wrong name");
            }
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
        }
    }
}
