package com.signlanguage.sign_learning_system.controller;// package com.signlanguage.sign_learning_system.controller;

import com.signlanguage.sign_learning_system.DTO.LoginResponse;
import com.signlanguage.sign_learning_system.model.User;
import com.signlanguage.sign_learning_system.payload.LoginRequest;
import com.signlanguage.sign_learning_system.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/admin")
public class AdminAuthController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostMapping("/login")
    public ResponseEntity<?> adminLogin(@RequestBody LoginRequest loginRequest) {
        Optional<User> optionalUser = userRepository.findByRegistrationNumber(loginRequest.getRegistrationNumber());

        if (optionalUser.isPresent()) {
            User user = optionalUser.get();

            if (!"ADMIN".equalsIgnoreCase(user.getRole())) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Hii account sio admin");
            }

            if (passwordEncoder.matches(loginRequest.getPassword(), user.getPassword())) {
                LoginResponse response = new LoginResponse(
                        user.getId(),
                        user.getRegistrationNumber(),
                        user.getFullName(),
                        user.getRole(),
                        null // admin haina level
                );
                return ResponseEntity.ok(response);
            } else {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Password si sahihi");
            }
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Admin haipo");
        }
    }
}
