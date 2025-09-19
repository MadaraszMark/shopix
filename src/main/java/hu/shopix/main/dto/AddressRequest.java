package hu.shopix.main.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema(description = "Cím létrehozásához/frissítéséhez használt DTO")
public class AddressRequest {

    @Schema(description = "Utca, házszám", example = "Váci út 22.")
    @NotBlank(message = "Az utca kötelező!")
    @Size(max = 255)
    private String street;

    @Schema(description = "Város", example = "Budapest")
    @NotBlank(message = "A város kötelező!")
    @Size(max = 120)
    private String city;

    @Schema(description = "Irányítószám", example = "1132")
    @NotBlank(message = "Az irányítószám kötelező!")
    @Size(max = 32)
    private String zipCode;

    @Schema(description = "Ország", example = "Magyarország")
    @NotBlank(message = "Az ország kötelező!")
    @Size(max = 120)
    private String country;
}

