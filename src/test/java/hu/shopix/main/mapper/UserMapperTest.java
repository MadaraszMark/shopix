package hu.shopix.main.mapper;

import hu.shopix.main.dto.AuthResponse;
import hu.shopix.main.dto.RegisterRequest;
import hu.shopix.main.dto.UserResponse;
import hu.shopix.main.model.User;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class UserMapperTest {

    private final UserMapper mapper = new UserMapper();

    @Test
    void testToEntity_withRole() {
        RegisterRequest req = RegisterRequest.builder()
                .email("admin@shopix.hu")
                .password("secret")
                .role("ADMIN")
                .build();

        User entity = mapper.toEntity(req, "hashedSecret");

        assertNotNull(entity);
        assertNull(entity.getId());
        assertEquals("admin@shopix.hu", entity.getEmail());
        assertEquals("hashedSecret", entity.getPasswordHash());
        assertEquals("ADMIN", entity.getRole());
    }

    @Test
    void testToEntity_withoutRole_defaultsToUser() {
        RegisterRequest req = RegisterRequest.builder()
                .email("user@shopix.hu")
                .password("pw")
                .build();

        User entity = mapper.toEntity(req, "hashedPw");

        assertNotNull(entity);
        assertEquals("user@shopix.hu", entity.getEmail());
        assertEquals("hashedPw", entity.getPasswordHash());
        assertEquals("USER", entity.getRole());
    }

    @Test
    void testToResponse() {
        LocalDateTime created = LocalDateTime.of(2025, 3, 1, 8, 0);

        User user = User.builder()
                .id(5L)
                .email("tester@shopix.hu")
                .passwordHash("x")
                .role("USER")
                .createdAt(created)
                .build();

        AuthResponse resp = mapper.toResponse(user, "jwt-token-123");

        assertNotNull(resp);
        assertEquals("tester@shopix.hu", resp.getEmail());
        assertEquals("jwt-token-123", resp.getToken());
        assertEquals("Bearer", resp.getTokenType());

        UserResponse uResp = resp.getUser();
        assertNotNull(uResp);
        assertEquals(5L, uResp.getId());
        assertEquals("tester@shopix.hu", uResp.getEmail());
        assertEquals("USER", uResp.getRole());
        assertEquals(created, uResp.getCreatedAt());
    }
}

