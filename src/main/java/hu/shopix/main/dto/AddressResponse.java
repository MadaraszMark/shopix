package hu.shopix.main.dto;

import java.time.LocalDateTime;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema(description = "Felhasználó címének adatai")
public class AddressResponse {

    @Schema(description = "Cím azonosítója", example = "42")
    private Long id;

    @Schema(description = "Utca, házszám", example = "Váci út 22.")
    private String street;

    @Schema(description = "Város", example = "Budapest")
    private String city;

    @Schema(description = "Irányítószám", example = "1132")
    private String zipCode;

    @Schema(description = "Ország", example = "Magyarország")
    private String country;

    @Schema(description = "Létrehozás ideje", example = "2025-09-18T12:34:56")
    private LocalDateTime createdAt;
}

