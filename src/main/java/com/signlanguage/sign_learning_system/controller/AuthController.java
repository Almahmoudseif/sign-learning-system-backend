package com.signlanguage.sign_learning_system.controller;

import com.signlanguage.sign_learning_system.model.User;
import com.signlanguage.sign_learning_system.payload.LoginRequest;
import com.signlanguage.sign_learning_system.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private UserRepository userRepository;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest) {
        // Tafuta user kwa registration number
        User user = userRepository.findByRegistrationNumber(loginRequest.getRegistrationNumber());

        if (user != null) {
            // Linga jina la user
            if (user.getFullName().equalsIgnoreCase(loginRequest.getFullName())) {
                return ResponseEntity.ok(user);
            } else {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Wrong name");
            }
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
        }
    }
}
