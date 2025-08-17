package com.signlanguage.sign_learning_system.util;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class PasswordHashGenerator {

    public static void main(String[] args) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

        // Default passwords
        String adminRawPassword = "admin123";
        String teacherRawPassword = "teacher123";

        // Generate hashes
        String adminHashed = encoder.encode(adminRawPassword);
        String teacherHashed = encoder.encode(teacherRawPassword);

        // Print results
        System.out.println("Admin raw password: " + adminRawPassword);
        System.out.println("Admin hashed password: " + adminHashed);
        System.out.println();
        System.out.println("Teacher raw password: " + teacherRawPassword);
        System.out.println("Teacher hashed password: " + teacherHashed);
    }
}
