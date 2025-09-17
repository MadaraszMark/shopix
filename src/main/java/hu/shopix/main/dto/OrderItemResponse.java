package hu.shopix.main.dto;

import java.math.BigDecimal;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema(description = "Rendelés egy tételének adatai")
public class OrderItemResponse {

    @Schema(description = "A termék azonosítója", example = "101")
    private Long productId;

    @Schema(description = "Mennyiség", example = "2")
    private Integer quantity;

    @Schema(description = "Egységár (bruttó)", example = "384000")
    private BigDecimal unitPrice;

    @Schema(description = "Sorösszeg (unitPrice * quantity)", example = "768000")
    private BigDecimal lineTotal;
}

