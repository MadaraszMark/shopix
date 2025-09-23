package hu.shopix.main.mapper;

import hu.shopix.main.dto.CategoryRequest;
import hu.shopix.main.dto.CategoryResponse;
import hu.shopix.main.model.Category;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CategoryMapperTest {

    private final CategoryMapper mapper = new CategoryMapper();

    @Test
    void testToEntity() {
        CategoryRequest req = CategoryRequest.builder()
                .name("Elektronika")
                .build();

        Category entity = mapper.toEntity(req);

        assertNotNull(entity);
        assertNull(entity.getId());
        assertEquals("Elektronika", entity.getName());
        assertNull(entity.getParent());
    }

    @Test
    void testToResponse_withoutParent() {
        Category entity = Category.builder()
                .id(10L)
                .name("Telefonok")
                .build();

        CategoryResponse resp = mapper.toResponse(entity);

        assertNotNull(resp);
        assertEquals(10L, resp.getId());
        assertEquals("Telefonok", resp.getName());
        assertNull(resp.getParent());
        assertNull(resp.getParentName());
    }

    @Test
    void testToResponse_withParent() {
        Category parent = Category.builder()
                .id(1L)
                .name("Elektronika")
                .build();

        Category child = Category.builder()
                .id(2L)
                .name("Telefonok")
                .parent(parent)
                .build();

        CategoryResponse resp = mapper.toResponse(child);

        assertNotNull(resp);
        assertEquals(2L, resp.getId());
        assertEquals("Telefonok", resp.getName());
        assertEquals(1L, resp.getParent());
        assertEquals("Elektronika", resp.getParentName());
    }
}

