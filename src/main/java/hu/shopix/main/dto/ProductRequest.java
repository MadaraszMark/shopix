package hu.shopix.main.dto;

import java.math.BigDecimal;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
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
@Schema(description = "Termék adatok létrehozásához szükséges DTO")
public class ProductRequest {
	
	@NotBlank(message = "A termék neve kötelező!")
    @Size(max = 255, message = "A termék neve legfeljebb 255 karakter lehet.")
    @Schema(example = "iPhone 14 Pro")
	private String name;
	
	@Schema(example = "Apple okostelefon 128GB tárhellyel")
	private String description;
	
	@NotNull(message = "Az ár kötelező!")
    @DecimalMin(value = "0.00", message = "Az ár nem lehet negatív.")
    @Schema(example = "399990.00")
	private BigDecimal price;
	
	@NotNull(message = "A kategória azonosító kötelező!")
    @Schema(example = "19")
	private Long categoryId;
	
	@Schema(description = "Aktív-e a termék", example = "true")
	private boolean active;
	
	

}
