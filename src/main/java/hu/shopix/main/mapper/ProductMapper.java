package hu.shopix.main.mapper;

import org.springframework.stereotype.Component;

import hu.shopix.main.dto.ProductRequest;
import hu.shopix.main.dto.ProductResponse;
import hu.shopix.main.model.Product;

@Component
public class ProductMapper {
	
	public Product toEntity(ProductRequest dto) {
        return Product.builder()
                .name(dto.getName())
                .description(dto.getDescription())
                .price(dto.getPrice())
                .active(dto.isActive())
                .build();
    }
	
	public ProductResponse toResponse(Product entity) {
        return ProductResponse.builder()
                .id(entity.getId())
                .name(entity.getName())
                .description(entity.getDescription())
                .price(entity.getPrice())
                .active(entity.isActive())
                .createdAt(entity.getCreatedAt())
                .categoryName(entity.getCategory() != null ? entity.getCategory().getName() : null)
                .categoryId(entity.getCategory() != null ? entity.getCategory().getId() : null)
                .stock(entity.getInventory() != null ? entity.getInventory().getQuantity() : null)
                .build();
    }

}
