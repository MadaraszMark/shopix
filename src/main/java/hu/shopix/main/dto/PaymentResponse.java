package hu.shopix.main.dto;

import java.time.LocalDateTime;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema(description = "A fizetés adatait visszaadó DTO")
public class PaymentResponse {

    @Schema(description = "Fizetés azonosítója", example = "101")
    private Long id;

    @Schema(description = "Kapcsolódó rendelés azonosítója", example = "55")
    private Long orderId;

    @Schema(description = "Fizetés státusza", example = "SUCCESS")
    private String status;

    @Schema(description = "Provider referencia (pl. tranzakció azonosító)", example = "PAY-123456789")
    private String providerRef;

    @Schema(description = "Fizetés létrehozásának időpontja", example = "2025-09-18T12:34:56")
    private LocalDateTime createdAt;
}

