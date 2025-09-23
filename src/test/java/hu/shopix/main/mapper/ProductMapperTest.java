package hu.shopix.main.mapper;

import hu.shopix.main.dto.ProductRequest;
import hu.shopix.main.dto.ProductResponse;
import hu.shopix.main.model.Category;
import hu.shopix.main.model.Inventory;
import hu.shopix.main.model.Product;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class ProductMapperTest {

    private final ProductMapper mapper = new ProductMapper();

    @Test
    void testToEntity() {
        ProductRequest req = ProductRequest.builder()
                .name("Okosóra")
                .description("Vízálló, GPS")
                .price(new BigDecimal("49999.90"))
                .active(true)
                .build();

        Product entity = mapper.toEntity(req);

        assertNotNull(entity);
        assertNull(entity.getId());
        assertEquals("Okosóra", entity.getName());
        assertEquals("Vízálló, GPS", entity.getDescription());
        assertEquals(new BigDecimal("49999.90"), entity.getPrice());
        assertTrue(entity.isActive());
    }

    @Test
    void testToResponse_withoutCategoryAndInventory() {
        LocalDateTime created = LocalDateTime.of(2025, 1, 15, 9, 0);

        Product entity = Product.builder()
                .id(10L)
                .name("Fülhallgató")
                .description("BT 5.3")
                .price(new BigDecimal("12999"))
                .active(true)
                .createdAt(created)
                .build();

        ProductResponse resp = mapper.toResponse(entity);

        assertNotNull(resp);
        assertEquals(10L, resp.getId());
        assertEquals("Fülhallgató", resp.getName());
        assertEquals("BT 5.3", resp.getDescription());
        assertEquals(new BigDecimal("12999"), resp.getPrice());
        assertTrue(resp.isActive());
        assertEquals(created, resp.getCreatedAt());
        assertNull(resp.getCategoryId());
        assertNull(resp.getCategoryName());
        assertNull(resp.getStock());
    }

    @Test
    void testToResponse_withCategoryAndInventory() {
        Category cat = Category.builder()
                .id(3L)
                .name("Elektronika")
                .build();

        Product entity = Product.builder()
                .id(20L)
                .name("Notebook")
                .description("16GB RAM, 512GB SSD")
                .price(new BigDecimal("399999"))
                .active(false)
                .category(cat)
                .createdAt(LocalDateTime.of(2025, 2, 1, 12, 0))
                .build();

        Inventory inv = Inventory.builder()
                .id(7L)
                .product(entity)
                .quantity(42)
                .build();
        entity.setInventory(inv);

        ProductResponse resp = mapper.toResponse(entity);

        assertNotNull(resp);
        assertEquals(20L, resp.getId());
        assertEquals("Notebook", resp.getName());
        assertEquals("16GB RAM, 512GB SSD", resp.getDescription());
        assertEquals(new BigDecimal("399999"), resp.getPrice());
        assertFalse(resp.isActive());
        assertEquals(3L, resp.getCategoryId());
        assertEquals("Elektronika", resp.getCategoryName());
        assertEquals(42, resp.getStock());
    }
}

