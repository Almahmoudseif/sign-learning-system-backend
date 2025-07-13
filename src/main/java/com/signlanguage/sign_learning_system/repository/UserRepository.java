package com.signlanguage.sign_learning_system.repository;

import com.signlanguage.sign_learning_system.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByRegistrationNumber(String registrationNumber);
}
