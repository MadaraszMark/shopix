package hu.shopix.main.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import hu.shopix.main.dto.AuthResponse;
import hu.shopix.main.dto.LoginRequest;
import hu.shopix.main.dto.RegisterRequest;
import hu.shopix.main.service.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController // REST API vezérlő, és minden metódus JSON választ ad vissza
@RequestMapping("/auth")
@RequiredArgsConstructor // Lombok -> Automatikusan létrehozza a konstruktort
@Tag(name = "Shopix API", description = "Regisztrációs / bejelentkezős REST API")
@Validated
public class AuthController {
	
	private final AuthService service;
	
	@PostMapping("/register")
    @Operation(summary = "Regisztráció",
        responses = {
            @ApiResponse(responseCode = "201", description = "Sikeres regisztráció", content = @Content(schema = @Schema(implementation = AuthResponse.class))),
            @ApiResponse(responseCode = "400", description = "Hibás bemenet"),
            @ApiResponse(responseCode = "409", description = "E-mail cím már foglalt")
        })
	public ResponseEntity<AuthResponse> register(@Valid @RequestBody RegisterRequest request){
		return ResponseEntity.status(HttpStatus.CREATED).body(service.register(request));
	}
	
	@PostMapping("/login")
    @Operation(summary = "Bejelentkezés",
        responses = {
            @ApiResponse(responseCode = "200", description = "Sikeres bejelentkezés", content = @Content(schema = @Schema(implementation = AuthResponse.class))),
            @ApiResponse(responseCode = "401", description = "Hibás e-mail cím vagy jelszó"),
            @ApiResponse(responseCode = "400", description = "Hibás bemenet")
        })
	public ResponseEntity<AuthResponse> login(@Valid @RequestBody LoginRequest req) {
        return ResponseEntity.ok(service.login(req));
    }

}
