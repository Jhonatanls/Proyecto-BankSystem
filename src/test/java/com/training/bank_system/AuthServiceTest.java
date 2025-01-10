package com.training.bank_system;
import com.training.bank_system.modelos.User;
import com.training.bank_system.repositorio.UserRepository;
import com.training.bank_system.config.JwtService;
import com.training.bank_system.service.auth.AuthService;
import com.training.bank_system.service.auth.AuthenticationRequest;
import com.training.bank_system.service.auth.AuthenticationResponse;
import com.training.bank_system.service.auth.RegisterRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class AuthServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private JwtService jwtService;

    @Mock
    private AuthenticationManager authenticationManager;

    @InjectMocks
    private AuthService authService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void register_shouldSaveUserAndReturnToken() {
        RegisterRequest request = new RegisterRequest("testuser", "Test User", "test@example.com", "password");
        User savedUser = User.builder()
                .username(request.getUsername())
                .name(request.getName())
                .email(request.getEmail())
                .password("encodedPassword")
                .build();

        when(passwordEncoder.encode(request.getPassword())).thenReturn("encodedPassword");
        when(userRepository.save(any(User.class))).thenReturn(savedUser);
        when(jwtService.generateToken(savedUser)).thenReturn("jwtToken");

        AuthenticationResponse response = authService.register(request);

        assertNotNull(response);
        assertEquals("jwtToken", response.getToken());
        verify(userRepository).save(any(User.class));
        verify(jwtService).generateToken(savedUser);
    }

    @Test
    void login_shouldAuthenticateAndReturnToken() {
        AuthenticationRequest request = new AuthenticationRequest("testuser", "password");
        User user = User.builder()
                .username(request.getUsername())
                .password("encodedPassword")
                .build();

        when(userRepository.findByUsername(request.getUsername())).thenReturn(Optional.of(user));
        when(jwtService.generateToken(user)).thenReturn("jwtToken");

        AuthenticationResponse response = authService.login(request);

        assertNotNull(response);
        assertEquals("jwtToken", response.getToken());
        verify(authenticationManager).authenticate(any(UsernamePasswordAuthenticationToken.class));
        verify(jwtService).generateToken(user);
    }

    @Test
    void login_shouldThrowExceptionWhenUserNotFound() {

        AuthenticationRequest request = new AuthenticationRequest("unknownUser", "password");
        when(userRepository.findByUsername(request.getUsername())).thenReturn(Optional.empty());

        assertThrows(UsernameNotFoundException.class, () -> authService.login(request));
        verify(authenticationManager).authenticate(any(UsernamePasswordAuthenticationToken.class));
        verify(userRepository).findByUsername(request.getUsername());
    }
}