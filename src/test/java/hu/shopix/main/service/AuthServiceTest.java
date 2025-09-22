package hu.shopix.main.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.server.ResponseStatusException;

import hu.shopix.main.dto.AuthResponse;
import hu.shopix.main.dto.LoginRequest;
import hu.shopix.main.dto.RegisterRequest;
import hu.shopix.main.dto.UserResponse;
import hu.shopix.main.mapper.UserMapper;
import hu.shopix.main.model.User;
import hu.shopix.main.repository.UserRepository;
import hu.shopix.main.security.JwtTokenUtil;

@ExtendWith(MockitoExtension.class)
public class AuthServiceTest {

    @Mock
    UserRepository userRepository;
    @Mock
    PasswordEncoder passwordEncoder;
    @Mock
    UserMapper userMapper;
    @Mock
    JwtTokenUtil jwtTokenUtil;
    @InjectMocks
    AuthService authService;

    @Test
    void register_ShouldThrowConflict_WhenEmailAlreadyExists() {
        RegisterRequest req = RegisterRequest.builder()
                .email("teszt@pelda.hu")
                .password("Secret123")
                .role(null)
                .build();
        when(userRepository.existsByEmail(req.getEmail())).thenReturn(true);
        ResponseStatusException ex = assertThrows(ResponseStatusException.class, 
            () -> authService.register(req));
        assertEquals(HttpStatus.CONFLICT, ex.getStatusCode());
        assertTrue(ex.getReason().contains("már használatban"), "Hibaüzenet tartalma");
        verify(userRepository).existsByEmail(req.getEmail());
        verify(userRepository, never()).save(any());
    }

    @Test
    void register_ShouldCreateUserAndReturnToken_WhenInputValidAndNewEmail() {
        RegisterRequest req = new RegisterRequest("newuser@test.com", "jelszo123", "USER");
        User newUser = User.builder()
                .id(1L).email(req.getEmail()).passwordHash("hashedpw").role("USER")
                .build();
        when(userRepository.existsByEmail(req.getEmail())).thenReturn(false);
        when(passwordEncoder.encode(req.getPassword())).thenReturn("hashedpw");
        when(userMapper.toEntity(eq(req), anyString())).thenReturn(newUser);
        when(userRepository.save(newUser)).thenReturn(newUser);
        when(jwtTokenUtil.generateToken(newUser.getEmail())).thenReturn("dummy-token");
        AuthResponse expectedResponse = AuthResponse.builder()
                .email(req.getEmail())
                .token("dummy-token").tokenType("Bearer")
                .user(UserResponse.builder()
                        .id(1L).email(req.getEmail()).role("USER").build())
                .build();
        when(userMapper.toResponse(eq(newUser), eq("dummy-token"))).thenReturn(expectedResponse);

        AuthResponse actual = authService.register(req);

        assertNotNull(actual);
        assertEquals("dummy-token", actual.getToken());
        assertEquals(req.getEmail(), actual.getEmail());
        assertEquals("USER", actual.getUser().getRole());
        verify(userRepository).existsByEmail(req.getEmail());
        verify(passwordEncoder).encode("jelszo123");
        verify(userRepository).save(newUser);
        verify(jwtTokenUtil).generateToken(req.getEmail());
        verify(userMapper).toResponse(newUser, "dummy-token");
    }
    
    @Test
    void login_ShouldThrowUnauthorized_WhenPasswordMismatch() {
        LoginRequest req = new LoginRequest("user@test.com", "wrongpw");
        User storedUser = User.builder()
                .id(5L).email(req.getEmail())
                .passwordHash("hashed-correct-pw").role("USER")
                .build();
        when(userRepository.findByEmail(req.getEmail()))
                .thenReturn(Optional.of(storedUser));
        when(passwordEncoder.matches(req.getPassword(), storedUser.getPasswordHash()))
                .thenReturn(false);
        ResponseStatusException ex = assertThrows(ResponseStatusException.class,
                () -> authService.login(req));
        assertEquals(HttpStatus.UNAUTHORIZED, ex.getStatusCode());
        assertTrue(ex.getReason().contains("Hibás e-mail cím vagy jelszó"));
        verify(userRepository).findByEmail(req.getEmail());
        verify(passwordEncoder).matches("wrongpw", "hashed-correct-pw");
        verify(jwtTokenUtil, never()).generateToken(any());
    }
}

