package hu.shopix.main.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
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
@Schema(description = "Rendelés létrehozásához szükséges adatok")
public class CreateOrderRequest {
	
	@NotNull
    @Schema(description = "Szállítási cím azonosítója", example = "12")
    private Long shippingAddressId;

    @Schema(description = "Számlázási cím azonosítója (ha nincs megadva, a szállítási cím lesz használva)", example = "15")
    private Long billingAddressId;

}


