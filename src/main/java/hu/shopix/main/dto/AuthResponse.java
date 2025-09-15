package hu.shopix.main.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema(description = "Authentikáció - Bejelentkezés után adatokat visszaadó DTO.")
public class AuthResponse {
	
	@Schema(description = "E-mail", example = "kata03@gmail.com")
	private String email;
	
	@Schema(description = "JWT token", example = "eyJhbGci6IkpXVCJ9...")
	private String token;
	
	@Schema(description = "Token típusa", example = "Bearer")
    @Builder.Default
	private String tokenType = "Bearer";
	
	@Schema(description = "Bejelentkezett felhasználó")
	private UserResponse user;

}
