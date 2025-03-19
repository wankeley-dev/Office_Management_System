package com.example.Office.Management.System.Service;




import com.example.Office.Management.System.Entity.User;
import com.example.Office.Management.System.Repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private static final Logger logger = LoggerFactory.getLogger(UserService.class);

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public void registerUser(User user) {
        // Check if email is already registered
        if (userRepository.findByEmail(user.getEmail()).isPresent()) {
            throw new RuntimeException("Email is already in use.");
        }

        // (Optional) Check if username (fullName) is already taken
        if (userRepository.findByFullName(user.getFullName()).isPresent()) {
            throw new RuntimeException("Username is already in use.");
        }

        // Encode password before saving
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        // Assign default role if not set
        if (user.getRole() == null) {
            user.setRole(User.Role.USER);
        }

        // Save user
        userRepository.save(user);
        logger.info("User registered successfully: {}", user.getEmail());
    }

    public Optional<User> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    public Optional<User> findById(Long id) {
        return userRepository.findById(id);
    }
}
