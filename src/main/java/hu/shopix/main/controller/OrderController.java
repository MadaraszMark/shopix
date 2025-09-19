package hu.shopix.main.controller;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import hu.shopix.main.dto.CreateOrderRequest;
import hu.shopix.main.dto.OrderResponse;
import hu.shopix.main.security.CurrentUser;
import hu.shopix.main.service.OrderService;
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

@RestController
@RequestMapping("/orders")
@Tag(name = "Shopix - Rendelések", description = "Saját rendelések")
@SecurityRequirement(name = "bearerAuth")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService service;
    private final CurrentUser currentUser;

    @PostMapping
    @Operation(summary = "Rendelés létrehozása az aktuális kosárból")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Rendelés létrejött", content = @Content(schema = @Schema(implementation = OrderResponse.class))),
            @ApiResponse(responseCode = "400", description = "A kosár üres vagy hibás"),
            @ApiResponse(responseCode = "404", description = "Felhasználó vagy kosár nem található")
    })
    public OrderResponse createFromCart(@AuthenticationPrincipal String email, @Valid @RequestBody(required = false) CreateOrderRequest request) {

        Long userId = currentUser.requireId(email);
        if (request == null) {
            request = new CreateOrderRequest();
        }
        return service.createFromCart(userId, request);
    }

    @GetMapping("/my")
    @Operation(summary = "Saját rendelések listázása")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Rendelések listája")
    })
    public Page<OrderResponse> listMyOrders(
            @AuthenticationPrincipal String email,
            @Parameter(description = "Lapozás és rendezés paraméterei") Pageable pageable) {
        Long userId = currentUser.requireId(email);
        return service.listMyOrders(userId, pageable);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Saját rendelés lekérése azonosító szerint")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Rendelés megtalálva", content = @Content(schema = @Schema(implementation = OrderResponse.class))),
            @ApiResponse(responseCode = "403", description = "Nem férsz hozzá ehhez a rendeléshez"),
            @ApiResponse(responseCode = "404", description = "Rendelés nem található")
    })
    public OrderResponse getById(@AuthenticationPrincipal String email, @PathVariable("id") Long orderId) {
        Long userId = currentUser.requireId(email);
        return service.getByIdForUser(orderId, userId);
    }
}

