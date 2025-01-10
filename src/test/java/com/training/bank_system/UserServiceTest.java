package com.training.bank_system;

import com.training.bank_system.modelos.User;
import com.training.bank_system.repositorio.UserRepository;
import com.training.bank_system.service.UserServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

public class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserServiceImpl userService;

    @BeforeEach
    void setUp(){
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void saveUser_ShouldReturnSavedUser(){

        User user = new User();
        user.setUserId(1L);
        user.setName("John Doe");
        user.setUsername("johnDoe");
        user.setPassword("securePassword");
        user.setEmail("john.doe@example.com");

        when(userRepository.save(user)).thenReturn(user);

        User resultUser = userService.saveUser(user);

        assertNotNull(resultUser);
        assertEquals("John Doe", resultUser.getName());
        assertEquals("johnDoe", resultUser.getUsername());
        assertEquals("securePassword", resultUser.getPassword());
        assertEquals("john.doe@example.com", resultUser.getEmail());
        verify(userRepository, times(1)).save(user);
    }

    @Test
    void getUser_ShouldReturnUserIfExists(){

        User user = new User();
        user.setUserId(1L);
        user.setName("John Doe");
        user.setUsername("johnDoe");
        user.setPassword("securePassword");
        user.setEmail("john.doe@example.com");

        when(userRepository.findById(1L)).thenReturn(Optional.of(user));

        // Act
        Optional<User> userResult = userService.getUser(1L);

        assertTrue(userResult.isPresent());
        assertEquals("John Doe", userResult.get().getName());
        verify(userRepository, times(1)).findById(1L);
    }

    @Test
    void getUser_ShouldReturnEmptyOptionalIfNotExists() {

        when(userRepository.findById(1L)).thenReturn(Optional.empty());

        Optional<User> result = userService.getUser(1L);

        assertFalse(result.isPresent());
        verify(userRepository, times(1)).findById(1L);
    }

    @Test
    void getAllUsers_ShouldReturnListOfUsers() {
        User user1 = new User();
        user1.setUserId(1L);
        user1.setName("John Doe");

        User user2 = new User();
        user2.setUserId(2L);
        user2.setName("Jane Doe");

        List<User> userList = Arrays.asList(user1, user2);
        when(userRepository.findAll()).thenReturn(userList);

        List<User> result = userService.getAllUsers();

        assertNotNull(result);
        assertEquals(2, result.size());
        verify(userRepository, times(1)).findAll();
    }

    @Test
    void editUser_ShouldReturnUpdatedUser() {
        User existingUser = new User();
        existingUser.setUserId(1L);
        existingUser.setName("Old Name");
        existingUser.setEmail("old@example.com");

        User updatedUser = new User();
        updatedUser.setName("New Name");
        updatedUser.setEmail("new@example.com");

        when(userRepository.findById(1L)).thenReturn(Optional.of(existingUser));
        when(userRepository.save(existingUser)).thenReturn(existingUser);

        User result = userService.editUser(1L, updatedUser);

        assertNotNull(result);
        assertEquals("New Name", result.getName());
        assertEquals("new@example.com", result.getEmail());
        verify(userRepository, times(1)).findById(1L);
        verify(userRepository, times(1)).save(existingUser);
    }

    @Test
    void deleteUser_ShouldReturnTrueIfUserDeleted() {
        doNothing().when(userRepository).deleteById(1L);

        boolean result = userService.deleteUser(1L);

        assertTrue(result);
        verify(userRepository, times(1)).deleteById(1L);
    }

    @Test
    void deleteUser_ShouldReturnFalseIfExceptionThrown() {
        doThrow(new RuntimeException("Error")).when(userRepository).deleteById(1L);

        boolean result = userService.deleteUser(1L);

        assertFalse(result);
        verify(userRepository, times(1)).deleteById(1L);
    }

}
