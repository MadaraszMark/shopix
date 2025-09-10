package hu.shopix.main.dto;

import io.swagger.v3.oas.annotations.media.Schema;
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
@Schema(description = "Kategória létrehozásához szükséges DTO")
public class CategoryRequest {
	
	@NotBlank(message = "A kategória név megadása kötelező!")
    @Size(max = 200, message = "A kategória neve legfeljebb 200 karakter lehet.")
    @Schema(description = "A kategória neve", example = "TV-k")
	private String name;

}
