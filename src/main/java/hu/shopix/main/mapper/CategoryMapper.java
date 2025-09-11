package hu.shopix.main.mapper;

import org.springframework.stereotype.Component;
import hu.shopix.main.dto.CategoryRequest;
import hu.shopix.main.dto.CategoryResponse;
import hu.shopix.main.model.Category;

@Component
public class CategoryMapper {

    public Category toEntity(CategoryRequest dto) {
        return Category.builder().name(dto.getName()).build();
    }

    public CategoryResponse toResponse(Category entity) {
        return CategoryResponse.builder()
                .id(entity.getId())
                .name(entity.getName())
                .parent(entity.getParent() != null ? entity.getParent().getId() : null)
                .parentName(entity.getParent() != null ? entity.getParent().getName() : null)
                .build();
    }
    
}

