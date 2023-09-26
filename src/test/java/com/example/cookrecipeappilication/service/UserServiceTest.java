package com.example.cookrecipeappilication.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

import com.example.cookrecipeappilication.model.User;
import com.example.cookrecipeappilication.repository.UserRepository;
import com.example.cookrecipeappilication.service.impl.UserServiceImpl;
import java.util.NoSuchElementException;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

class UserServiceTest {
    private User user;
    private UserRepository userRepository;
    private UserService userService;

    @BeforeEach
    void setUp() {
        user = new User();
        user.setId(1L);
        user.setEmail("fill@gmail.com");
        user.setFirstName("Fill");
        user.setLastName("Brandon");
        user.setPassword("12345678");
        user.setRole(User.Role.CUSTOMER);
        userRepository = Mockito.mock(UserRepository.class);
        userService = new UserServiceImpl(userRepository);
    }

    @Test
    void save_ok() {
        when(userRepository.save(user)).thenReturn(user);
        User actualUser = userService.save(user);
        assertEquals(user.getId(), actualUser.getId());
        assertEquals(user.getEmail(), actualUser.getEmail());
        assertEquals(user.getPassword(), actualUser.getPassword());
        assertEquals(user.getUsername(), actualUser.getUsername());
        assertEquals(user.getLastName(), actualUser.getLastName());
        assertEquals(user.getRole(), actualUser.getRole());
    }

    @Test
    void updateUserRole_ok() {
        String newRole = "MANAGER";
        Long id = 1L;
        when(userRepository.findById(id)).thenReturn(Optional.of(user));
        userService.updateUserRole(id, newRole);
        assertEquals(newRole, user.getRole().name());
    }

    @Test
    void updateUserRole_withNull_notOk() {
        Long id = 1L;
        String message = "The role name cannot be empty.";
        when(userRepository.findById(id)).thenReturn(Optional.of(user));
        RuntimeException exception = assertThrows(RuntimeException.class,
                () -> userService.updateUserRole(id, null));
        assertEquals(message, exception.getMessage());
    }

    @Test
    void updateUserRole_withEmptyRole_notOk() {
        Long id = 1L;
        String newRole = "";
        String message = "The role name cannot be empty.";
        when(userRepository.findById(id)).thenReturn(Optional.of(user));
        RuntimeException exception = assertThrows(RuntimeException.class,
                () -> userService.updateUserRole(id, newRole));
        assertEquals(message, exception.getMessage());
    }

    @Test
    void updateUserRole_withNotExistRole_notOk() {
        Long id = 1L;
        String newRole = "Role";
        String message = "The role name is only possible CUSTOMER or MANAGER.";
        when(userRepository.findById(id)).thenReturn(Optional.of(user));
        RuntimeException exception = assertThrows(RuntimeException.class,
                () -> userService.updateUserRole(id, newRole));
        assertEquals(message, exception.getMessage());
    }

    @Test
    void updateUserRole_withNotExistUser_notOk() {
        Long id = 999L;
        String newRole = "MANAGER";
        String message = "Can't find user by id: " + id;
        when(userRepository.findById(id)).thenReturn(Optional.empty());
        NoSuchElementException exception = assertThrows(NoSuchElementException.class,
                () -> userService.updateUserRole(id, newRole));
        assertEquals(message, exception.getMessage());
    }

    @Test
    void getByEmail_ok() {
        String email = "fill@gmail.com";
        when(userRepository.findByEmail(email)).thenReturn(Optional.of(user));
        User actual = userService.getByEmail(email);
        assertEquals(user.getId(), actual.getId());
        assertEquals(user.getEmail(), actual.getEmail());
        assertEquals(user.getPassword(), actual.getPassword());
        assertEquals(user.getUsername(), actual.getUsername());
        assertEquals(user.getLastName(), actual.getLastName());
        assertEquals(user.getRole(), actual.getRole());
    }

    @Test
    void getByEmail_withIncorrectEmail_notOk() {
        String wrongEmail = "wrongEmail@gmail.com";
        String message = "Can't find user by email: " + wrongEmail;
        when(userRepository.findByEmail(wrongEmail)).thenReturn(Optional.empty());
        NoSuchElementException exception = assertThrows(NoSuchElementException.class,
                () -> userService.getByEmail(wrongEmail));
        assertEquals(message, exception.getMessage());
    }
}
