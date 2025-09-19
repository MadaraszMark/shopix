package hu.shopix.main.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import hu.shopix.main.dto.UserResponse;
import hu.shopix.main.mapper.UserMapper;
import hu.shopix.main.model.User;
import hu.shopix.main.repository.UserRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@Tag(name = "Users")
@RestController
@RequestMapping("/auth/users")
@RequiredArgsConstructor
public class UserController {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    @Operation(summary = "Saját profil", security = { @SecurityRequirement(name = "bearerAuth") })
    @GetMapping("/me")
    public ResponseEntity<UserResponse> me(@AuthenticationPrincipal String email) {
        if (email == null) throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Nincs bejelentkezve.");
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Felhasználó nem található."));
        return ResponseEntity.ok(
                userMapper.toResponse(user, null).getUser()
        );
    }
}
