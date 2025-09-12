package hu.shopix.main.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
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
@Schema(description = "Kosárhoz módosításához szükséges DTO")
public class CartItemUpdateRequest {
	
	@NotNull
    @Schema(description = "A termék ID-ja", example = "11")
	private Long productId;
	
	@NotNull
	@Min(0)
	@Schema(description = "A mennyiség (0 = törlés, vagy legalább 1)", example = "2")
	private Integer quantity;
	
}
