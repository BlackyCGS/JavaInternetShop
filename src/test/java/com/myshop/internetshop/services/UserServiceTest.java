package com.myshop.internetshop.services;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.myshop.internetshop.classes.dto.UserDto;
import com.myshop.internetshop.classes.dto.UserRequest;
import com.myshop.internetshop.classes.entities.Order;
import com.myshop.internetshop.classes.entities.User;
import com.myshop.internetshop.classes.exceptions.ConflictException;
import com.myshop.internetshop.classes.exceptions.NotFoundException;
import com.myshop.internetshop.classes.repositories.UserRepository;
import com.myshop.internetshop.classes.services.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;

    private User user;
    private Order order;
    private UserRequest userRequest;

    @BeforeEach
    void setUp() {
        user = new User();
        user.setId(1);
        user.setName("JohnDoe");
        user.setEmail("johndoe@example.com");
        user.setPassword("password123");
        order = new Order();
        order.setId(1);
        order.setUser(user);
        user.addNewOrder(order);
        userRequest = new UserRequest();
        userRequest.setName("JohnDoe");
        userRequest.setEmail("johndoe@example.com");
        userRequest.setPassword("password123");
    }

    @Test
    void createUser_shouldSaveUser_whenUserDoesNotExist() {
        when(userRepository.existsByEmail(userRequest.getEmail())).thenReturn(false);
        when(userRepository.existsByName(userRequest.getName())).thenReturn(false);
        when(userRepository.save(any())).thenReturn(user);

        User result = userService.createUser(userRequest);

        assertNotNull(result);
        assertEquals("JohnDoe", result.getName());
        verify(userRepository, times(1)).save(any());
    }

    @Test
    void createUser_shouldThrowConflictException_whenEmailAlreadyExists() {
        when(userRepository.existsByEmail(userRequest.getEmail())).thenReturn(true);

        assertThrows(ConflictException.class, () -> userService.createUser(userRequest));
    }

    @Test
    void createUser_shouldThrowConflictException_whenNameAlreadyExists() {
        when(userRepository.existsByName(userRequest.getName())).thenReturn(true);

        assertThrows(ConflictException.class, () -> userService.createUser(userRequest));
    }

    @Test
    void deleteUser_shouldDeleteUser_whenUserExists() {
        when(userRepository.existsById(1)).thenReturn(true);
        doNothing().when(userRepository).deleteById(1);

        assertDoesNotThrow(() -> userService.deleteUser(1));
        verify(userRepository, times(1)).deleteById(1);
    }

    @Test
    void deleteUser_shouldThrowNotFoundException_whenUserDoesNotExist() {
        when(userRepository.existsById(1)).thenReturn(false);

        assertThrows(NotFoundException.class, () -> userService.deleteUser(1));
    }

    @Test
    void getUserById_shouldReturnUserDto_whenUserExists() {
        when(userRepository.existsById(1)).thenReturn(true);
        when(userRepository.findById(1)).thenReturn(user);

        UserDto result = userService.getUserById(1);

        assertNotNull(result);
        assertEquals("JohnDoe", result.getName());
    }

    @Test
    void getUserById_shouldThrowNotFoundException_whenUserDoesNotExist() {
        when(userRepository.existsById(1)).thenReturn(false);

        assertThrows(NotFoundException.class, () -> userService.getUserById(1));
    }

    @Test
    void updateUser_shouldUpdateUser_whenUserExists() {
        when(userRepository.existsById(1)).thenReturn(true);
        when(userRepository.findById(1)).thenReturn(user);
        when(userRepository.save(any())).thenReturn(user);

        UserDto result = userService.updateUser(1, userRequest);

        assertNotNull(result);
        assertEquals("JohnDoe", result.getName());
    }

    @Test
    void updateUser_shouldThrowNotFoundException_whenUserDoesNotExist() {
        when(userRepository.existsById(1)).thenReturn(false);

        assertThrows(NotFoundException.class, () -> userService.updateUser(1, userRequest));
    }

    @Test
    void existsById_shouldReturnTrue_whenUserExists() {
        when(userRepository.existsById(1)).thenReturn(true);

        assertTrue(userService.existsById(1));
    }

    @Test
    void existsById_shouldReturnFalse_whenUserDoesNotExist() {
        when(userRepository.existsById(1)).thenReturn(false);

        assertFalse(userService.existsById(1));
    }

    @Test
    void findByUserId_shouldReturnUser_whenUserExists() {
        when(userRepository.existsById(1)).thenReturn(true);
        when(userRepository.findById(1)).thenReturn(user);

        User result = userService.findByUserId(1);

        assertNotNull(result);
        assertEquals("JohnDoe", result.getName());
    }

    @Test
    void findByUserId_shouldThrowNotFoundException_whenUserDoesNotExist() {
        when(userRepository.existsById(1)).thenReturn(false);

        assertThrows(NotFoundException.class, () -> userService.findByUserId(1));
    }
}
