package com.example.cookrecipeappilication.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import com.example.cookrecipeappilication.dto.request.UserLoginRequestDto;
import com.example.cookrecipeappilication.dto.request.UserRegisterRequestDto;
import com.example.cookrecipeappilication.dto.response.JwtAuthenticationResponseDto;
import com.example.cookrecipeappilication.model.User;
import com.example.cookrecipeappilication.repository.UserRepository;
import com.example.cookrecipeappilication.security.JwtService;
import com.example.cookrecipeappilication.service.impl.AuthenticationServiceImpl;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.security.crypto.password.PasswordEncoder;

class AuthenticationServiceTest {
    private UserRegisterRequestDto request;
    private UserLoginRequestDto loginRequestDto;
    private AuthenticationService authenticationService;
    private UserRepository userRepository;
    private PasswordEncoder passwordEncoder;
    private JwtService jwtService;

    @BeforeEach
    public void setUp() {
        request = new UserRegisterRequestDto("Vlad", "Taylor", "vlad@example.com",
                "password123", "password123");
        loginRequestDto = new UserLoginRequestDto("john@example.com", "password123");
        userRepository = mock(UserRepository.class);
        passwordEncoder = mock(PasswordEncoder.class);
        jwtService = mock(JwtService.class);
        authenticationService = new AuthenticationServiceImpl(userRepository, passwordEncoder,
                jwtService);
    }

    @Test
    public void register_withExistingEmail_notOk() {
        request.setEmail("existing@example.com");
        String message = "The user with this email already exists";
        when(userRepository.findByEmail(request.getEmail())).thenReturn(Optional.of(new User()));
        RuntimeException exception = assertThrows(RuntimeException.class,
                () -> authenticationService.register(request));
        assertEquals(message, exception.getMessage());
    }

    @Test
    public void register_invalidPassword_lessThanSevenCharacter_notOk() {
        request.setPassword("short");
        String message = "The password cannot be empty or less than 7 characters";
        RuntimeException exception = assertThrows(RuntimeException.class,
                () -> authenticationService.register(request));
        assertEquals(message, exception.getMessage());
    }

    @Test
    public void register_invalidPassword_null_notOk() {
        request.setPassword(null);
        String message = "The password cannot be empty or less than 7 characters";
        RuntimeException exception = assertThrows(RuntimeException.class,
                () -> authenticationService.register(request));
        assertEquals(message, exception.getMessage());
    }

    @Test
    public void register_ok() {
        User user = User.builder()
                .firstName("Vlad")
                .lastName("Taylor")
                .email("vlad@example.com")
                .password("encodedPassword")
                .role(User.Role.CUSTOMER)
                .build();
        when(userRepository.findByEmail(request.getEmail())).thenReturn(Optional.empty());
        when(passwordEncoder.encode(request.getPassword())).thenReturn("encodedPassword");
        when(userRepository.save(any(User.class))).thenReturn(user);
        when(jwtService.generateToken(user)).thenReturn("token123");
        JwtAuthenticationResponseDto response = authenticationService.register(request);
        assertNotNull(response);
    }

    @Test
    public void register_emptyPassword_notOk() {
        request.setPassword("   ");
        String message = "The password cannot be empty or less than 7 characters";
        RuntimeException exception = assertThrows(RuntimeException.class,
                () -> authenticationService.register(request));
        assertEquals(message, exception.getMessage());
    }

    @Test
    public void login_ok() {
        User user = User.builder()
                .email("john@example.com")
                .password("encodedPassword")
                .build();
        when(userRepository.findByEmail(loginRequestDto.getEmail()))
                .thenReturn(Optional.of(user));
        when(passwordEncoder.matches(loginRequestDto.getPassword(), user.getPassword()))
                .thenReturn(true);
        when(jwtService.generateToken(user)).thenReturn("token123");
        JwtAuthenticationResponseDto response = authenticationService.login(loginRequestDto);
        assertNotNull(response);
        assertEquals("token123", response.getToken());
    }

    @Test
    public void login_notExistUserByEmail_notOk() {
        String email = "notExistEmail@.com";
        String message = "The user doesn't exist by this email: " + email;
        loginRequestDto.setEmail(email);
        when(userRepository.findByEmail(email)).thenReturn(Optional.empty());
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> authenticationService.login(loginRequestDto));
        assertEquals(message, exception.getMessage());
    }

    @Test
    public void login_invalidPassword_notOk() {
        loginRequestDto.setPassword("invalidPassword");
        String message = "Invalid email or password";
        User user = User.builder()
                .email("john@example.com")
                .password("password123")
                .build();
        when(userRepository.findByEmail(loginRequestDto.getEmail())).thenReturn(Optional.of(user));
        when(passwordEncoder.matches(loginRequestDto.getPassword(), user.getPassword()))
                .thenReturn(false);
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> authenticationService.login(loginRequestDto));
        assertEquals(message, exception.getMessage());
    }
}
