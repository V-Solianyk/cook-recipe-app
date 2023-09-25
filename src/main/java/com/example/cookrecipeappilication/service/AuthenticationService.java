package com.example.cookrecipeappilication.service;

import com.example.cookrecipeappilication.dto.request.UserLoginRequestDto;
import com.example.cookrecipeappilication.dto.request.UserRegisterRequestDto;
import com.example.cookrecipeappilication.dto.response.JwtAuthenticationResponseDto;

public interface AuthenticationService {
    JwtAuthenticationResponseDto register(UserRegisterRequestDto request);

    JwtAuthenticationResponseDto login(UserLoginRequestDto request);
}
