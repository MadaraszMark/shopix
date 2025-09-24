package hu.shopix.main.controller;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import hu.shopix.main.dto.CategoryResponse;
import hu.shopix.main.service.CategoryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@RestController // REST API vezérlő, és minden metódus JSON választ ad vissza
@RequiredArgsConstructor // Lombok -> Automatikusan létrehozza a konstruktort
@RequestMapping("/categories")
@Tag(name = "Shopix - Kategóriák", description = "Publikus kategória végpontok")
public class CategoryController {
	
	private final CategoryService service;
	
	@GetMapping
    @Operation(summary = "Összes kategória lekérése", description = "Visszaadja a kategóriák listáját")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Sikeres lekérés"),
        @ApiResponse(responseCode = "500", description = "Szerverhiba")
    })
	public Page<CategoryResponse> getAllCategory(Pageable pageable){
		return service.getAllCategories(pageable);
	}
	
	@GetMapping("/by-id/{id}")
    @Operation(summary = "Kategória lekérése ID alapján")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Megtalált kategória"),
        @ApiResponse(responseCode = "404", description = "Nem található")
    })
	public CategoryResponse getCategoryById(@PathVariable("id") Long id) {
		return service.getCategoryById(id);
	}
	
}

