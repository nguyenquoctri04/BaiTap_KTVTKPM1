package com.myapp.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class UserServiceTest {

    @InjectMocks
    private UserService userService;

    @Mock
    private Map<String, String> mockUsers;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        mockUsers = new HashMap<>();
        mockUsers.put("testUser", "testPassword");
    }

    @Test
    public void testAuthenticate_ValidCredentials() {
        // Arrange
        when(mockUsers.containsKey("testUser")).thenReturn(true);
        when(mockUsers.get("testUser")).thenReturn("testPassword");

        // Act
        String token = userService.authenticate("testUser", "testPassword");

        // Assert
        assertNotNull(token);
    }

    @Test
    public void testAuthenticate_InvalidCredentials() {
        // Arrange
        when(mockUsers.containsKey("invalidUser")).thenReturn(false);

        // Act
        String token = userService.authenticate("invalidUser", "wrongPassword");

        // Assert
        assertNull(token);
    }
}