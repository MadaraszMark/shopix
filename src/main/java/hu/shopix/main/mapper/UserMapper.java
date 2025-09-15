package hu.shopix.main.mapper;

import org.springframework.stereotype.Component;

import hu.shopix.main.dto.AuthResponse;
import hu.shopix.main.dto.RegisterRequest;
import hu.shopix.main.dto.UserResponse;
import hu.shopix.main.model.User;

@Component
public class UserMapper {
	
	public User toEntity(RegisterRequest request, String encodedPassword) {
		return User.builder()
				.email(request.getEmail())
				.passwordHash(encodedPassword)
				.role(request.getRole() != null ? request.getRole() : "USER")
				.build();
	}
	
	public AuthResponse toResponse(User user, String token) {
		return AuthResponse.builder()
				.email(user.getEmail())
				.token(token)
				.tokenType("Bearer")
				.user(UserResponse.builder()
                        .id(user.getId())
                        .email(user.getEmail())
                        .role(user.getRole())
                        .createdAt(user.getCreatedAt())
                        .build())
				.build();
	}

}
