package hu.shopix.main.controller;

import org.springframework.beans.factory.annotation.Autowired;
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

@RestController
@RequestMapping("/categories")
@Tag(name = "Shopix - Kategóriák", description = "Publikus kategória végpontok")
public class CategoryController {
	
	private final CategoryService service;
	
	@Autowired
	public CategoryController(CategoryService service) { // // Az @Autowired megmondja a Springnek, hogy a konstruktor paramétereit automatikusan adja be a Spring contextből. Konstruktor injekció.
		this.service = service;
	}
	
	@GetMapping
    @Operation(summary = "Összes kategória lekérése", description = "Visszaadja a kategóriák listáját lapozással és rendezéssel.")
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

