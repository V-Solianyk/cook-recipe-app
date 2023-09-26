package com.example.cookrecipeappilication.service.impl;

import com.example.cookrecipeappilication.dto.request.UserLoginRequestDto;
import com.example.cookrecipeappilication.dto.request.UserRegisterRequestDto;
import com.example.cookrecipeappilication.dto.response.JwtAuthenticationResponseDto;
import com.example.cookrecipeappilication.model.User;
import com.example.cookrecipeappilication.repository.UserRepository;
import com.example.cookrecipeappilication.security.JwtService;
import com.example.cookrecipeappilication.service.AuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    @Override
    public JwtAuthenticationResponseDto register(UserRegisterRequestDto request) {
        checkInputData(request);
        var user = User.builder()
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(User.Role.CUSTOMER)
                .build();
        userRepository.save(user);
        var jwt = jwtService.generateToken(user);
        return JwtAuthenticationResponseDto.builder().token(jwt).build();
    }

    @Override
    public JwtAuthenticationResponseDto login(UserLoginRequestDto request) {
        var user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new IllegalArgumentException("The user doesn't exist by"
                        + " this email: " + request.getEmail()));
        checkPassword(request, user);
        var jwt = jwtService.generateToken(user);
        return JwtAuthenticationResponseDto.builder().token(jwt).build();
    }

    private void checkPassword(UserLoginRequestDto request, User user) {
        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new IllegalArgumentException("Invalid email or password");
        }
    }

    private void checkInputData(UserRegisterRequestDto request) {
        if (userRepository.findByEmail(request.getEmail()).isPresent()) {
            throw new RuntimeException("The user with this email already exists");
        }
        if (request.getPassword() == null || request.getPassword().isBlank()
                || request.getPassword().length() < 7) {
            throw new RuntimeException("The password cannot be empty or less than 7 characters");
        }
        if (!request.getPassword().equals(request.getRepeatPassword())) {
            throw new IllegalArgumentException("Passwords do not match");
        }
        if (request.getFirstName() == null || request.getFirstName().isBlank()
                || request.getEmail() == null || request.getEmail().isBlank()
                || request.getLastName() == null || request.getLastName().isBlank()) {
            throw new RuntimeException("invalid data fields cannot be empty");
        }
    }
}
