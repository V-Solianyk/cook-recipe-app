package com.example.cookrecipeappilication.service.impl;

import com.example.cookrecipeappilication.model.User;
import com.example.cookrecipeappilication.repository.UserRepository;
import com.example.cookrecipeappilication.service.UserService;
import java.util.NoSuchElementException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    @Override
    public User save(User user) {
        return userRepository.save(user);
    }

    @Override
    public User updateUserRole(Long userId, String role) {
        String newRole = checkInput(role);
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new NoSuchElementException("Can't find user by id: " + userId));
        user.setRole(User.Role.valueOf(newRole));
        return userRepository.save(user);
    }

    @Override
    public User getByEmail(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new NoSuchElementException("Can't find user by"
                        + " email: " + email));
    }

    private String checkInput(String userRole) {
        if (userRole == null || userRole.isBlank()) {
            throw new RuntimeException("The role name cannot be empty.");
        }
        String role = userRole.toUpperCase();
        if (!role.equals(User.Role.MANAGER.name()) && !role
                .equals(User.Role.CUSTOMER.name())) {
            throw new RuntimeException("The role name is only possible CUSTOMER or MANAGER.");
        }
        return role;
    }
}
