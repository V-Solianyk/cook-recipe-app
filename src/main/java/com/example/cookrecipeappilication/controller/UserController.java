package com.example.cookrecipeappilication.controller;

import com.example.cookrecipeappilication.dto.UserResponseDto;
import com.example.cookrecipeappilication.mapper.UserResponseDtoMapper;
import com.example.cookrecipeappilication.model.User;
import com.example.cookrecipeappilication.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;
    private final UserResponseDtoMapper userResponseDto;

    @PutMapping("/{id}/role")
    public UserResponseDto update(@PathVariable Long id,
                                  @RequestParam User.Role userRole) { // or STRING
        User user = userService.updateUserRole(id, userRole);
        return userResponseDto.mapToDto(user);
    }
}
