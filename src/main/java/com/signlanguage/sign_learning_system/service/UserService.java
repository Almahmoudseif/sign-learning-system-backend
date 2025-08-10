package com.signlanguage.sign_learning_system.service;

import com.signlanguage.sign_learning_system.DTO.RegisterTeacherRequest;
import com.signlanguage.sign_learning_system.DTO.TeacherProfileResponse;
import com.signlanguage.sign_learning_system.enums.LessonLevel;
import com.signlanguage.sign_learning_system.model.User;
import com.signlanguage.sign_learning_system.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public User registerUser(User user) {
        if (user.getLevel() == null) {
            user.setLevel(LessonLevel.BEGINNER);
        }
        user.setRegistrationNumber(generateUserRegistrationNumber());
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }

    public User registerTeacher(RegisterTeacherRequest request) {
        User teacher = new User();
        teacher.setFullName(request.getFullName());
        teacher.setEmail(request.getEmail());
        teacher.setPassword(passwordEncoder.encode(request.getPassword()));
        teacher.setRole("TEACHER");
        teacher.setRegistrationNumber(generateTeacherRegistrationNumber());

        return userRepository.save(teacher);
    }

    public Optional<User> loginUser(String registrationNumber, String password) {
        Optional<User> userOptional = userRepository.findByRegistrationNumber(registrationNumber);
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            if (passwordEncoder.matches(password, user.getPassword())) {
                return userOptional;
            }
        }
        return Optional.empty();
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public List<User> getUsersByRole(String role) {
        return userRepository.findByRole(role);
    }

    public List<User> getAllStudents() {
        return userRepository.findByRole("student");
    }

    public User getUserById(Long id) {
        return userRepository.findById(id).orElse(null);
    }

    public void deleteUserById(Long id) {
        userRepository.deleteById(id);
    }

    public Optional<User> getUserByRegistrationNumber(String regNumber) {
        return userRepository.findByRegistrationNumber(regNumber);
    }

    public User updateUser(Long id, User updatedUser) {
        User existingUser = getUserById(id);
        if (existingUser != null) {
            existingUser.setFullName(updatedUser.getFullName());
            existingUser.setEmail(updatedUser.getEmail());
            existingUser.setPassword(passwordEncoder.encode(updatedUser.getPassword()));
            existingUser.setRole(updatedUser.getRole());
            existingUser.setLevel(updatedUser.getLevel());
            return userRepository.save(existingUser);
        }
        return null;
    }

    public List<User> assignLevelToStudentsWithoutLevel(LessonLevel defaultLevel) {
        List<User> students = userRepository.findByRole("student");
        List<User> updated = new ArrayList<>();

        for (User user : students) {
            if (user.getLevel() == null) {
                user.setLevel(defaultLevel);
                updated.add(userRepository.save(user));
            }
        }

        return updated;
    }

    private String generateUserRegistrationNumber() {
        Random random = new Random();
        String reg;
        do {
            reg = "SL-" + (1000 + random.nextInt(9000));
        } while (userRepository.findByRegistrationNumber(reg).isPresent());
        return reg;
    }

    private String generateTeacherRegistrationNumber() {
        Random random = new Random();
        String reg;
        do {
            reg = "T-" + (1000 + random.nextInt(9000));
        } while (userRepository.findByRegistrationNumber(reg).isPresent());
        return reg;
    }

    public TeacherProfileResponse getTeacherProfileById(Long id) {
        Optional<User> optionalUser = userRepository.findById(id);
        if (!optionalUser.isPresent()) {
            throw new RuntimeException("User not found with ID: " + id);
        }

        User user = optionalUser.get();

        if (!"TEACHER".equalsIgnoreCase(user.getRole())) {
            throw new RuntimeException("User is not a teacher");
        }

        return new TeacherProfileResponse(
                user.getId(),
                user.getFullName(),
                user.getEmail(),
                user.getRegistrationNumber()
        );
    }

    // Hii ndio method mpya unayotaka:
    public LessonLevel getUserLevel(Long userId) {
        Optional<User> userOpt = userRepository.findById(userId);
        return userOpt.map(User::getLevel).orElse(null);
    }
}
