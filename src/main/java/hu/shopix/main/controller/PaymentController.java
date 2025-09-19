package hu.shopix.main.controller;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import hu.shopix.main.dto.PaymentResponse;
import hu.shopix.main.security.CurrentUser;
import hu.shopix.main.service.PaymentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/orders")
@Tag(name = "Shopix - Fizetések", description = "Rendelések fizetése")
@SecurityRequirement(name = "bearerAuth")
@RequiredArgsConstructor
public class PaymentController {

    private final PaymentService paymentService;
    private final CurrentUser currentUser;

    @PostMapping("/{id}/pay")
    @Operation(summary = "Rendelés kifizetése")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Fizetés létrejött vagy már létezik", content = @Content(schema = @Schema(implementation = PaymentResponse.class))),
        @ApiResponse(responseCode = "403", description = "Nem a saját rendelésed"),
        @ApiResponse(responseCode = "404", description = "Rendelés nem található")
    })
    public PaymentResponse payOrder(
            @AuthenticationPrincipal String email,
            @Parameter(description = "A fizetendő rendelés azonosítója") @PathVariable("id") Long orderId) {

        Long userId = currentUser.requireId(email);
        return paymentService.pay(orderId, userId);
    }
}

