package hu.shopix.main.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
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
@Schema(description = "Regisztráció létrehozásához szükséges DTO")
public class RegisterRequest {
	
	@Schema(description = "E-mail cím", example = "kata03@gmail.com")
    @NotBlank(message = "Az e-mail nem lehet üres")
    @Email(message = "Érvényes e-mail címet adj meg")
	private String email;
	
	@NotBlank(message = "A jelszó megadása kötelező")
    @Size(min = 6, message = "A jelszónak legalább 6 karakter hosszúnak kell lennie")
    @Schema(description = "Jelszó", example = "zsed2-231sa-sfa5")
	private String password;
	
	@Schema(description = "Felhasználói szerepkör (alapértelmezett: USER)", example = "USER")
	private String role = "USER";

}
