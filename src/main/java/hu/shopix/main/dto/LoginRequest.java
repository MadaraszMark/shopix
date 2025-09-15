package hu.shopix.main.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
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
@Schema(description = "Belépéshez szükséges DTO")
public class LoginRequest {
	
	@NotBlank(message = "E-mail megadása kötelező")
	@Schema(description = "E-mail cím", example = "kata03@gmail.com")
	private String email;
	
	@NotBlank(message = "A jelszó megadása kötelező")
	@Schema(description = "Jelszó", example = "zsed2-231sa-sfa5")
	private String password;

}
