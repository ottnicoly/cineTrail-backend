package com.nicolyott.cineTrail.service;

import com.nicolyott.cineTrail.dto.AuthenticationDTO;
import com.nicolyott.cineTrail.entity.user.User;
import com.nicolyott.cineTrail.entity.user.UserRole;
import com.nicolyott.cineTrail.exception.user.UserAlreadyExistsException;
import com.nicolyott.cineTrail.infra.security.TokenService;
import com.nicolyott.cineTrail.repository.UserRepository;
import com.nicolyott.cineTrail.service.auth.AuthenticationService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@SpringBootTest
class AuthenticationServiceTest {

    @InjectMocks
    private AuthenticationService authenticationService;

    @Mock
    private AuthenticationManager authenticationManager;

    @Mock
    private UserRepository userRepository;

    @Mock
    private TokenService tokenService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("Should return token when credentials are valid")
    void loginCase1() {
        AuthenticationDTO authData = new AuthenticationDTO("user", "password");
        User mockUser = new User();
        mockUser.setLogin("user");
        mockUser.setPassword("hashedPassword");
        mockUser.setRole(UserRole.USER);

        Authentication authenticationMock = mock(Authentication.class);
        when(authenticationMock.getPrincipal()).thenReturn(mockUser);
        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class))).thenReturn(authenticationMock);
        when(tokenService.generateToken(mockUser)).thenReturn("valid-token");

        String token = authenticationService.login(authData);

        assertEquals("valid-token", token);
        verify(authenticationManager, times(1)).authenticate(any(UsernamePasswordAuthenticationToken.class));
        verify(tokenService, times(1)).generateToken(mockUser);
    }

    @Test
    @DisplayName("Should throw exception for invalid credentials")
    void loginCase2() {
        AuthenticationDTO authData = new AuthenticationDTO("user", "wrongpassword");

        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class)))
                .thenThrow(BadCredentialsException.class);

        assertThrows(BadCredentialsException.class, () -> {
            authenticationService.login(authData);
        });

        verify(authenticationManager, times(1)).authenticate(any(UsernamePasswordAuthenticationToken.class));
        verify(tokenService, never()).generateToken(any());
    }

    @Test
    @DisplayName("Should register successfully if user does not exist")
    void registerCase1() {
        String login = "newUser";
        UserRole role = UserRole.USER;
        String password = "password";

        when(userRepository.findByLogin(login)).thenReturn(null);

        authenticationService.register(login, role, password);

        verify(userRepository, times(1)).save(any(User.class));
    }

    @Test
    @DisplayName("Should throw UserAlreadyExistsException if user already exists")
    void registerCase2() {
        String login = "existingUser";
        UserRole role = UserRole.USER;
        String password = "password";

        User existingUser = new User();
        existingUser.setLogin(login);

        when(userRepository.findByLogin(login)).thenReturn(existingUser);

        assertThrows(UserAlreadyExistsException.class, () -> {
            authenticationService.register(login, role, password);
        });

        verify(userRepository, never()).save(any());
    }
}