package com.signlanguage.sign_learning_system.config;

import com.signlanguage.sign_learning_system.model.User;
import com.signlanguage.sign_learning_system.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class AdminSetup implements CommandLineRunner {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) throws Exception {
        // Angalia kama admin tayari ipo
        if (!userRepository.findByRegistrationNumber("ADMIN001").isPresent()) {
            User admin = new User();
            admin.setFullName("Super Admin");
            admin.setEmail("admin@example.com");
            admin.setRegistrationNumber("ADMIN001");
            admin.setPassword(passwordEncoder.encode("admin123")); // unaweza kubadilisha
            admin.setRole("ADMIN");
            userRepository.save(admin);
            System.out.println("Admin mpya ameundwa: ADMIN001 / admin123");
        } else {
            System.out.println("Admin tayari ipo");
        }
    }
}
