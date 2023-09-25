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
    public User updateUserRole(Long userId, User.Role newRole) {
        User user = userRepository.findById(userId).orElse(null);
        if (user != null) {
            user.setRole(newRole);
            return userRepository.save(user);
        }
        return null;
    }

    @Override
    public User getByEmail(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new NoSuchElementException("Can't find user by email:" + email));
    }
}
