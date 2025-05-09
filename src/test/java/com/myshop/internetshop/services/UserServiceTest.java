package com.myshop.internetshop.services;

import com.myshop.internetshop.classes.dto.UserDto;
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
import org.springframework.security.test.context.support.WithMockUser;


import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;

    private User testUser;
    private UserDto testUserDto;

    @BeforeEach
    void setUp() {
        testUser = new User();
        testUser.setId(1);
        testUser.setName("Test User");
        testUser.setEmail("test@example.com");
        testUser.setPassword("password");

        testUserDto = new UserDto();
        testUserDto.setName("Test User");
        testUserDto.setEmail("test@example.com");
        testUserDto.setPassword("password");
    }

    @Test
    void createUser_Success() {
        // Arrange
        when(userRepository.existsByEmail(anyString())).thenReturn(false);
        when(userRepository.existsByName(anyString())).thenReturn(false);
        when(userRepository.save(any(User.class))).thenReturn(testUser);

        // Act
        User result = userService.createUser(testUserDto);

        // Assert
        assertNotNull(result);
        assertEquals("Test User", result.getName());
        assertEquals("test@example.com", result.getEmail());
        verify(userRepository).save(any(User.class));
    }

    @Test
    void createUser_EmailExists_ThrowsConflictException() {
        // Arrange
        when(userRepository.existsByEmail(anyString())).thenReturn(true);

        // Act & Assert
        assertThrows(ConflictException.class, () -> userService.createUser(testUserDto));
        verify(userRepository, never()).save(any(User.class));
    }

    @Test
    void createUser_NameExists_ThrowsConflictException() {
        // Arrange
        when(userRepository.existsByEmail(anyString())).thenReturn(false);
        when(userRepository.existsByName(anyString())).thenReturn(true);

        // Act & Assert
        assertThrows(ConflictException.class, () -> userService.createUser(testUserDto));
        verify(userRepository, never()).save(any(User.class));
    }

    @Test
    @WithMockUser("hasRole('ADMIN')")
    void deleteUser_Success() {
        // Arrange
        when(userRepository.existsById(1)).thenReturn(true);

        // Act
        userService.deleteUser(1);

        // Assert
        verify(userRepository).deleteById(1);
    }

    @Test
    @WithMockUser("hasRole('ADMIN')")
    void deleteUser_NotFound_ThrowsNotFoundException() {
        // Arrange
        when(userRepository.existsById(1)).thenReturn(false);

        // Act & Assert
        assertThrows(NotFoundException.class, () -> userService.deleteUser(1));
        verify(userRepository, never()).deleteById(anyInt());
    }

    @Test
    @WithMockUser("hasRole('ADMIN')")
    void getUserById_Success() {
        // Arrange
        when(userRepository.existsById(1)).thenReturn(true);
        when(userRepository.safeFindById(1)).thenReturn(testUser);

        // Act
        UserDto result = userService.getUserById(1);

        // Assert
        assertNotNull(result);
        assertEquals("Test User", result.getName());
        assertEquals("test@example.com", result.getEmail());
    }

    @Test
    @WithMockUser("hasRole('ADMIN')")
    void getUserById_NotFound_ThrowsNotFoundException() {
        // Arrange
        when(userRepository.existsById(1)).thenReturn(false);

        // Act & Assert
        assertThrows(NotFoundException.class, () -> userService.getUserById(1));
    }

    @Test
    @WithMockUser("hasRole('ADMIN')")
    void updateUser_Success() {
        // Arrange
        UserDto updateDto = new UserDto();
        updateDto.setName("Updated Name");
        updateDto.setEmail("updated@example.com");
        updateDto.setPassword("newpassword");

        when(userRepository.existsById(1)).thenReturn(true);
        when(userRepository.findById(1)).thenReturn(testUser);
        when(userRepository.save(any(User.class))).thenReturn(testUser);

        // Act
        UserDto result = userService.updateUser(1, updateDto);

        // Assert
        assertNotNull(result);
        verify(userRepository).save(any(User.class));
    }

    @Test
    @WithMockUser("hasRole('ADMIN')")
    void updateUser_PartialUpdate_Success() {
        // Arrange
        UserDto partialUpdateDto = new UserDto();
        partialUpdateDto.setName("Updated Name");

        when(userRepository.existsById(1)).thenReturn(true);
        when(userRepository.findById(1)).thenReturn(testUser);
        when(userRepository.save(any(User.class))).thenReturn(testUser);

        // Act
        UserDto result = userService.updateUser(1, partialUpdateDto);

        // Assert
        assertNotNull(result);
        verify(userRepository).save(any(User.class));
    }

    @Test
    @WithMockUser("hasRole('ADMIN')")
    void updateUser_NotFound_ThrowsNotFoundException() {
        // Arrange
        when(userRepository.existsById(1)).thenReturn(false);

        // Act & Assert
        assertThrows(NotFoundException.class, () -> userService.updateUser(1, testUserDto));
        verify(userRepository, never()).save(any(User.class));
    }

    @Test
    @WithMockUser("hasRole('ADMIN')")
    void existsById_ReturnsTrue() {
        // Arrange
        when(userRepository.existsById(1)).thenReturn(true);

        // Act
        boolean result = userService.existsById(1);

        // Assert
        assertTrue(result);
    }

    @Test
    @WithMockUser("hasRole('ADMIN')")
    void existsById_ReturnsFalse() {
        // Arrange
        when(userRepository.existsById(1)).thenReturn(false);

        // Act
        boolean result = userService.existsById(1);

        // Assert
        assertFalse(result);
    }

    @Test
    @WithMockUser("hasRole('ADMIN')")
    void findByUserId_Success() {
        // Arrange
        when(userRepository.existsById(1)).thenReturn(true);
        when(userRepository.findById(1)).thenReturn(testUser);

        // Act
        User result = userService.findByUserId(1);

        // Assert
        assertNotNull(result);
        assertEquals("Test User", result.getName());
    }

    @Test
    @WithMockUser("hasRole('ADMIN')")
    void findByUserId_NotFound_ThrowsNotFoundException() {
        // Arrange
        when(userRepository.existsById(1)).thenReturn(false);

        // Act & Assert
        assertThrows(NotFoundException.class, () -> userService.findByUserId(1));
    }
}