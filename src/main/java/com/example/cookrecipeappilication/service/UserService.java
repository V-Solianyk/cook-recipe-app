package com.example.cookrecipeappilication.service;

import com.example.cookrecipeappilication.model.User;

public interface UserService {
    User save(User user);

    User updateUserRole(Long userId, String role);

    User getByEmail(String email);
}
