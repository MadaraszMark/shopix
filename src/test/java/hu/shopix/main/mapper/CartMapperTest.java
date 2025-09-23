package hu.shopix.main.mapper;

import hu.shopix.main.dto.CartItemResponse;
import hu.shopix.main.dto.CartResponse;
import hu.shopix.main.model.Cart;
import hu.shopix.main.model.CartItem;
import hu.shopix.main.model.Product;
import hu.shopix.main.model.User;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class CartMapperTest {

    private final CartMapper mapper = new CartMapper();

    @Test
    void testToResponseItem() {
        User user = User.builder().id(7L).email("user@test.com").build();
        Cart cart = Cart.builder()
                .id(11L)
                .user(user)
                .status("OPEN")
                .items(new ArrayList<>())
                .build();

        Product product = Product.builder()
                .id(99L)
                .name("Teszt termék")
                .price(new BigDecimal("1234.56"))
                .build();

        CartItem item = CartItem.builder()
                .id(555L)
                .cart(cart)
                .product(product)
                .quantity(3)
                .unitPriceSnapshot(new BigDecimal("1234.56"))
                .build();

        CartItemResponse resp = mapper.toResponseItem(item);

        assertNotNull(resp);
        assertEquals(555L, resp.getId());
        assertEquals(11L, resp.getCartId());
        assertEquals(99L, resp.getProductId());
        assertEquals(3, resp.getQuantity());
        assertEquals(new BigDecimal("1234.56"), resp.getUnitPriceSnapshot());
    }

    @Test
    void testToResponseCart_withItems() {
        LocalDateTime created = LocalDateTime.of(2025, 1, 1, 10, 0);
        LocalDateTime updated = LocalDateTime.of(2025, 1, 2, 12, 30);

        User user = User.builder().id(1L).email("buyer@shopix.hu").build();

        Cart cart = Cart.builder()
                .id(22L)
                .user(user)
                .status("OPEN")
                .createdAt(created)
                .updatedAt(updated)
                .items(new ArrayList<>())
                .build();

        Product p1 = Product.builder().id(101L).name("Termék A").price(new BigDecimal("100.00")).build();
        Product p2 = Product.builder().id(102L).name("Termék B").price(new BigDecimal("200.00")).build();

        CartItem i1 = CartItem.builder()
                .id(1001L)
                .cart(cart)
                .product(p1)
                .quantity(2)
                .unitPriceSnapshot(new BigDecimal("100.00"))
                .build();

        CartItem i2 = CartItem.builder()
                .id(1002L)
                .cart(cart)
                .product(p2)
                .quantity(1)
                .unitPriceSnapshot(new BigDecimal("200.00"))
                .build();

        cart.getItems().addAll(List.of(i1, i2));

        CartResponse resp = mapper.toResponseCart(cart);

        assertNotNull(resp);
        assertEquals(22L, resp.getId());
        assertEquals(1L, resp.getUserId());
        assertEquals("OPEN", resp.getStatus());
        assertEquals(created, resp.getCreatedAt());
        assertEquals(updated, resp.getUpdatedAt());

        assertNotNull(resp.getItems());
        assertEquals(2, resp.getItems().size());

        CartItemResponse r1 = resp.getItems().get(0);
        CartItemResponse r2 = resp.getItems().get(1);

        assertEquals(1001L, r1.getId());
        assertEquals(22L, r1.getCartId());
        assertEquals(101L, r1.getProductId());
        assertEquals(2, r1.getQuantity());
        assertEquals(new BigDecimal("100.00"), r1.getUnitPriceSnapshot());

        assertEquals(1002L, r2.getId());
        assertEquals(22L, r2.getCartId());
        assertEquals(102L, r2.getProductId());
        assertEquals(1, r2.getQuantity());
        assertEquals(new BigDecimal("200.00"), r2.getUnitPriceSnapshot());
    }

    @Test
    void testToResponseCart_emptyItems() {
        User user = User.builder().id(5L).email("empty@shopix.hu").build();
        Cart cart = Cart.builder()
                .id(33L)
                .user(user)
                .status("OPEN")
                .items(new ArrayList<>())
                .build();

        CartResponse resp = mapper.toResponseCart(cart);

        assertNotNull(resp);
        assertEquals(33L, resp.getId());
        assertEquals(5L, resp.getUserId());
        assertEquals("OPEN", resp.getStatus());
        assertNotNull(resp.getItems());
        assertTrue(resp.getItems().isEmpty());
    }
}

