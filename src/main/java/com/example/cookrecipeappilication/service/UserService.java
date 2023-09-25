package com.example.cookrecipeappilication.service;

import com.example.cookrecipeappilication.model.User;

public interface UserService {
    User save(User user);

    User updateUserRole(Long userId, User.Role newRole);

    User getByEmail(String email);
}
