package hu.shopix.main.controller;

import org.springdoc.core.annotations.ParameterObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import hu.shopix.main.dto.ProductResponse;
import hu.shopix.main.service.ProductService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/products")
@Tag(name = "Shopix - Termékek", description = "Publikus termékek")
public class ProductController {
	
	private final ProductService  productService;

	@Autowired
	public ProductController(ProductService productService) { // Az @Autowired megmondja a Springnek, hogy a konstruktor paramétereit automatikusan adja be a Spring contextből. Konstruktor injekció.
		this.productService = productService;
	}
	
	@GetMapping
    @Operation(summary = "Összes termék lekérése", description = "Visszaadja a termékek listáját")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Sikeres lekérés"),
        @ApiResponse(responseCode = "500", description = "Szerverhiba")
    })
	public Page<ProductResponse> getAllProducts(@ParameterObject Pageable pageable){
		return productService.getAllProducts(pageable);
	}
	
	@GetMapping("/by-id/{id}")
    @Operation(summary = "Termékek lekérése ID alapján")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Megtalált termék(ek)"),
        @ApiResponse(responseCode = "404", description = "Nem található")
    })
	public ProductResponse getProductById(@PathVariable("id") Long id) {
		return productService.getProductById(id);
	}
	
	@GetMapping("/by-name/{name}")
    @Operation(summary = "Termékek lekérése a nevük alapján")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Megtalált termék(ek)"),
        @ApiResponse(responseCode = "404", description = "Nem található")
    })
	public Page<ProductResponse> getProductByName(@PathVariable("name") String name, @ParameterObject Pageable pageable){
		return productService.getProductByName(name, pageable);
	}
	
	@GetMapping("/active")
	@Operation(summary = "Csak az aktív termékek lekérése")
	@ApiResponses({
	    @ApiResponse(responseCode = "200", description = "Aktív termékek listája")
	})
	public Page<ProductResponse> getActiveProducts(Pageable pageable) {
	    return productService.getProductByActiveTrue(pageable);
	}

	@GetMapping("/by-category/{categoryId}")
    @Operation(summary = "Termékek lekérése a kategória alapján")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Megtalált termék(ek)"),
        @ApiResponse(responseCode = "404", description = "Nem található")
    })
	public Page<ProductResponse> getProductsByCategory(@PathVariable("categoryId") Long categoryId, @ParameterObject Pageable pageable){
		return productService.getProductsByCategory(categoryId, pageable);
	}

}
