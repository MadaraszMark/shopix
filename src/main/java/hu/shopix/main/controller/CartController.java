package hu.shopix.main.controller;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import hu.shopix.main.dto.AddCartItemRequest;
import hu.shopix.main.dto.CartItemUpdateRequest;
import hu.shopix.main.dto.CartResponse;
import hu.shopix.main.security.CurrentUser;
import hu.shopix.main.service.CartService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController // REST API vezérlő, és minden metódus JSON választ ad vissza
@RequestMapping("/cart")
@Tag(name = "Shopix - Kosár", description = "Saját kosár műveletek")
@SecurityRequirement(name = "bearerAuth") // Végpontok JWT tokennel védettek
@RequiredArgsConstructor // Lombok -> Automatikusan létrehozza a konstruktort
public class CartController {

    private final CartService service;
    private final CurrentUser currentUser;


    @GetMapping("/me")
    @Operation(summary = "Saját nyitott kosár lekérése")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Kosár megtalálva"),
            @ApiResponse(responseCode = "404", description = "Kosár nem található")
    })
    public CartResponse getMyCart(@AuthenticationPrincipal String email) {
        Long userId = currentUser.requireId(email);
        return service.getOpenCartByUser(userId);
    }

    @PostMapping("/add")
    @Operation(summary = "Termék hozzáadása a saját kosárhoz")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Kosár frissítve"),
            @ApiResponse(responseCode = "404", description = "Felhasználó vagy termék nem található")
    })
    public CartResponse addItem(@AuthenticationPrincipal String email, @Valid @RequestBody AddCartItemRequest request) {
        Long userId = currentUser.requireId(email);
        return service.addItem(userId, request);
    }

    @PutMapping("/update")
    @Operation(summary = "Saját kosár tétel frissítése/törlése (quantity=0 → törlés)")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Kosár frissítve"),
            @ApiResponse(responseCode = "404", description = "Kosár vagy tétel nem található")
    })
    public CartResponse updateCart(@AuthenticationPrincipal String email, @Valid @RequestBody CartItemUpdateRequest request) {
        Long userId = currentUser.requireId(email);
        return service.updateItem(userId, request);
    }

    @DeleteMapping("/clear")
    @Operation(summary = "Saját kosár teljes kiürítése")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Kosár kiürítve")
    })
    public CartResponse clearMyCart(@AuthenticationPrincipal String email) {
        Long userId = currentUser.requireId(email);
        return service.clearCart(userId);
    }

    // Régi  végpontok

    @Deprecated
    @GetMapping("/by-user/{userId}")
    @Operation(summary = "Aktuális kosár lekérése felhasználó szerint (DEPRECATED)")
    public CartResponse getOpenCart(@AuthenticationPrincipal String email,@PathVariable("userId") Long userId) {
        currentUser.ensureSameUser(userId, email);
        return service.getOpenCartByUser(userId);
    }

    @Deprecated
    @PostMapping("/add/{userId}")
    @Operation(summary = "Termék hozzáadása a kosárhoz (DEPRECATED)")
    public CartResponse addItemOld(@AuthenticationPrincipal String email, @PathVariable("userId") Long userId, @Valid @RequestBody AddCartItemRequest request) {
        currentUser.ensureSameUser(userId, email);
        return service.addItem(userId, request);
    }

    @Deprecated
    @PutMapping("/update/{userId}")
    @Operation(summary = "A kosár frissítése (DEPRECATED)")
    public CartResponse updateCartOld(@AuthenticationPrincipal String email, @PathVariable("userId") Long userId, @Valid @RequestBody CartItemUpdateRequest request) {
        currentUser.ensureSameUser(userId, email);
        return service.updateItem(userId, request);
    }

    @Deprecated
    @DeleteMapping("/clear/{userId}")
    @Operation(summary = "Kosár teljes kiürítése (DEPRECATED)")
    public CartResponse clearCartOld(@AuthenticationPrincipal String email, @PathVariable("userId") Long userId) {
        currentUser.ensureSameUser(userId, email);
        return service.clearCart(userId);
    }
}

