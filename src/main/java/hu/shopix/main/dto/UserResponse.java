package hu.shopix.main.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.time.LocalDateTime;

@Getter @Setter
@NoArgsConstructor 
@AllArgsConstructor 
@Builder
@Schema(description = "Felhasználó publikus adatai")
public class UserResponse {

	@Schema(description = "Felhasználó azonosítója", example = "1")
    private Long id;

	@Schema(description = "E-mail cím", example = "kata03@gmail.com")
    private String email;

    @Schema(description = "Szerepkör", example = "USER")
    private String role;

    @Schema(description = "Létrehozás időpontja", example = "2025-08-18T10:15:30")
    private LocalDateTime createdAt;
}

