package com.example.demo.auth.service;

import com.example.demo.auth.domain.User;
import com.example.demo.auth.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.transaction.Transactional;

@Service
public class AdminInitializationService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private boolean adminInitialized = false; // Флаг для відстеження ініціалізації

    public AdminInitializationService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Transactional
    @PostConstruct
    public void initializeAdminUser() {
        if (!adminInitialized && !userRepository.existsByUsername("admin")) {
            User adminUser = new User();
            adminUser.setUsername("admin");
            adminUser.setPassword(passwordEncoder.encode("super_secret_password"));
            userRepository.save(adminUser);
            adminInitialized = true; // Позначаємо, що ініціалізація відбулася
        }
    }
}
