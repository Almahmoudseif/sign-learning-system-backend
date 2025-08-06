package com.signlanguage.sign_learning_system.repository;

import com.signlanguage.sign_learning_system.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);
    Optional<User> findByRegistrationNumber(String registrationNumber);
    List<User> findByRole(String role);
    Optional<User> findByFullNameAndRegistrationNumber(String fullName, String registrationNumber);
}
