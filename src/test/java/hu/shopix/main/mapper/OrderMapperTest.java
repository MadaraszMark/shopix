package hu.shopix.main.mapper;

import hu.shopix.main.dto.OrderItemResponse;
import hu.shopix.main.dto.OrderResponse;
import hu.shopix.main.model.Order;
import hu.shopix.main.model.OrderItem;
import hu.shopix.main.model.Product;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class OrderMapperTest {

    private final OrderMapper mapper = new OrderMapper();

    @Test
    void testToResponseItem() {
        Product product = Product.builder()
                .id(100L)
                .name("Laptop")
                .price(new BigDecimal("250000"))
                .build();

        OrderItem item = OrderItem.builder()
                .product(product)
                .quantity(2)
                .unitPrice(new BigDecimal("250000"))
                .build();

        OrderItemResponse resp = mapper.toResponseItem(item);

        assertNotNull(resp);
        assertEquals(100L, resp.getProductId());
        assertEquals(2, resp.getQuantity());
        assertEquals(new BigDecimal("250000"), resp.getUnitPrice());
        assertEquals(new BigDecimal("500000"), resp.getLineTotal());
    }

    @Test
    void testToResponse() {
        Product p1 = Product.builder().id(1L).name("Egér").price(new BigDecimal("5000")).build();
        Product p2 = Product.builder().id(2L).name("Billentyűzet").price(new BigDecimal("10000")).build();

        OrderItem i1 = OrderItem.builder().product(p1).quantity(3).unitPrice(new BigDecimal("5000")).build();
        OrderItem i2 = OrderItem.builder().product(p2).quantity(1).unitPrice(new BigDecimal("10000")).build();

        LocalDateTime created = LocalDateTime.of(2025, 1, 10, 15, 30);

        Order order = Order.builder()
                .id(55L)
                .status("NEW")
                .createdAt(created)
                .totalGross(new BigDecimal("25000"))
                .items(List.of(i1, i2))
                .shippingStreet("Fő utca 1.")
                .shippingCity("Budapest")
                .shippingZip("1011")
                .shippingCountry("Magyarország")
                .billingStreet("Kossuth utca 2.")
                .billingCity("Debrecen")
                .billingZip("4024")
                .billingCountry("Magyarország")
                .build();

        OrderResponse resp = mapper.toResponse(order);

        assertNotNull(resp);
        assertEquals(55L, resp.getId());
        assertEquals(new BigDecimal("25000"), resp.getTotalGross());
        assertEquals("NEW", resp.getStatus());
        assertEquals(created, resp.getCreatedAt());

        assertEquals("Fő utca 1.", resp.getShippingStreet());
        assertEquals("Budapest", resp.getShippingCity());
        assertEquals("1011", resp.getShippingZip());
        assertEquals("Magyarország", resp.getShippingCountry());

        assertEquals("Kossuth utca 2.", resp.getBillingStreet());
        assertEquals("Debrecen", resp.getBillingCity());
        assertEquals("4024", resp.getBillingZip());
        assertEquals("Magyarország", resp.getBillingCountry());

        assertNotNull(resp.getItems());
        assertEquals(2, resp.getItems().size());

        OrderItemResponse r1 = resp.getItems().get(0);
        OrderItemResponse r2 = resp.getItems().get(1);

        assertEquals(1L, r1.getProductId());
        assertEquals(3, r1.getQuantity());
        assertEquals(new BigDecimal("15000"), r1.getLineTotal());

        assertEquals(2L, r2.getProductId());
        assertEquals(1, r2.getQuantity());
        assertEquals(new BigDecimal("10000"), r2.getLineTotal());
    }
}

