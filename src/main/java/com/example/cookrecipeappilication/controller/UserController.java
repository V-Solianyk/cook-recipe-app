package com.example.cookrecipeappilication.controller;

import com.example.cookrecipeappilication.dto.response.UserResponseDto;
import com.example.cookrecipeappilication.mapper.UserResponseDtoMapper;
import com.example.cookrecipeappilication.model.User;
import com.example.cookrecipeappilication.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;
    private final UserResponseDtoMapper userResponseDto;

    @PatchMapping("/{id}")
    public ResponseEntity<UserResponseDto> update(@PathVariable Long id,
                                                  @RequestParam String role) {
        User user = userService.updateUserRole(id, role);
        return ResponseEntity.ok(userResponseDto.mapToDto(user));
    }
}
