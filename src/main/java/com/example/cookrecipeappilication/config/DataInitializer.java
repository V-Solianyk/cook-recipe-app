package com.example.cookrecipeappilication.config;

import com.example.cookrecipeappilication.model.User;
import com.example.cookrecipeappilication.repository.UserRepository;
import jakarta.annotation.PostConstruct;
import java.util.Optional;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class DataInitializer {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public DataInitializer(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @PostConstruct
    public void inject() {
        Optional<User> byEmailVlad = userRepository.findByEmail("manager@com.ua");
        if (byEmailVlad.isEmpty()) {
            User manager1 = User.builder()
                    .email("manager@com.ua")
                    .password(passwordEncoder.encode("1234"))
                    .firstName("Vlad")
                    .lastName("Nasir")
                    .role(User.Role.MANAGER)
                    .build();
            userRepository.save(manager1);
        }
    }
}
