package hu.shopix.main.controller;

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
import hu.shopix.main.service.CartService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/cart")
@Tag(name = "Shopix - Kosár", description = "Publikus kosár")
public class CartController {
	
	private final CartService service;

	public CartController(CartService service) {
		this.service = service;
	}
	
	@GetMapping("/by-user/{userId}")
    @Operation(summary = "Aktuális kosár lekérése felhasználó szerint")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Megtalált kosár"),
        @ApiResponse(responseCode = "404", description = "Nem található")
    })
	public CartResponse getOpenCart(@PathVariable("userId") Long userId) {
		return service.getOpenCartByUser(userId);
	}
	
	@PostMapping("/add/{userId}")
    @Operation(summary = "Termék hozzáadása a kosárhoz")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Kosár frissítve"),
        @ApiResponse(responseCode = "404", description = "Felhasználó vagy termék nem található")
    })
	public CartResponse addItem(@PathVariable("userId") Long userId, @Valid @RequestBody AddCartItemRequest request) {
		return service.addItem(userId, request);
	}
	
	@PutMapping("/update/{userId}")
    @Operation(summary = "A kosár frissítve")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Kosár frissítve"),
        @ApiResponse(responseCode = "404", description = "Kosár vagy termék nem található")
    })
	public CartResponse updateCart(@PathVariable("userId") Long userId, @Valid @RequestBody CartItemUpdateRequest request) {
		return service.updateItem(userId, request);
	}
	
	@DeleteMapping("/clear/{userId}")
    @Operation(summary = "Kosár teljes kiürítése")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Kosár kiürítve"),
        @ApiResponse(responseCode = "404", description = "Kosár nem található")
    })
	public CartResponse clearCart(@PathVariable("userId") Long userId) {
		return service.clearCart(userId);
	}
}
