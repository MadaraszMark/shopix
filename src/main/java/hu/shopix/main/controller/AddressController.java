package hu.shopix.main.controller;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import hu.shopix.main.dto.AddressRequest;
import hu.shopix.main.dto.AddressResponse;
import hu.shopix.main.security.CurrentUser;
import hu.shopix.main.service.AddressService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController // REST API vezérlő, és minden metódus JSON választ ad vissza
@RequestMapping("/addresses")
@Tag(name = "Shopix - Címek", description = "Saját címek kezelése")
@SecurityRequirement(name = "bearerAuth") // Végpontok JWT tokennel védettek
@RequiredArgsConstructor // Lombok -> Automatikusan létrehozza a konstruktort
public class AddressController {

    private final AddressService service;
    private final CurrentUser currentUser;

    @GetMapping("/me")
    @Operation(summary = "Saját címek listázása")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Címek listája")
    })
    public Page<AddressResponse> listMyAddresses(@AuthenticationPrincipal String email, @Parameter(description = "Lapozás és rendezés paraméterei") Pageable pageable) {
        Long userId = currentUser.requireId(email);
        return service.listMy(userId, pageable);
    }

    @PostMapping("/me")
    @Operation(summary = "Új cím létrehozása a bejelentkezett felhasználóhoz")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Cím létrehozva", content = @Content(schema = @Schema(implementation = AddressResponse.class))),
        @ApiResponse(responseCode = "400", description = "Validációs hiba")
    })
    public AddressResponse createMyAddress(@AuthenticationPrincipal String email, @Valid @RequestBody AddressRequest request) {
        Long userId = currentUser.requireId(email);
        return service.create(userId, request);
    }

    @PutMapping("/me/{id}")
    @Operation(summary = "Saját cím frissítése")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Cím frissítve", content = @Content(schema = @Schema(implementation = AddressResponse.class))),
        @ApiResponse(responseCode = "403", description = "Nem saját cím"),
        @ApiResponse(responseCode = "404", description = "Cím nem található")
    })
    public AddressResponse updateMyAddress(@AuthenticationPrincipal String email, @PathVariable("id") Long addressId, @Valid @RequestBody AddressRequest request) {
        Long userId = currentUser.requireId(email);
        return service.update(userId, addressId, request);
    }

    @DeleteMapping("/me/{id}")
    @Operation(summary = "Saját cím törlése")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Cím törölve"),
        @ApiResponse(responseCode = "403", description = "Nem saját cím"),
        @ApiResponse(responseCode = "404", description = "Cím nem található")
    })
    public void deleteMyAddress(@AuthenticationPrincipal String email, @PathVariable("id") Long addressId) {
        Long userId = currentUser.requireId(email);
        service.delete(userId, addressId);
    }
}

