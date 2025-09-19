package hu.shopix.main.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema(description = "Rendelés létrehozásakor megadható opciók")
public class CreateOrderRequest {

    @Schema(description = "Szállítási cím azonosítója (saját címeid közül)", example = "12")
    private Long shippingAddressId;

    @Schema(description = "Számlázási cím azonosítója (saját címeid közül). Ha nincs megadva, a szállítási cím lesz használva.", example = "13")
    private Long billingAddressId;
}

