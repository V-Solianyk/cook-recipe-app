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
                .orElseThrow(() -> new IllegalArgumentException("Invalid email or password"));
        var jwt = jwtService.generateToken(user);
        return JwtAuthenticationResponseDto.builder().token(jwt).build();
    }
}
