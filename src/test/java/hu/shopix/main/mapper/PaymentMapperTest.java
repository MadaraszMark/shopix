package hu.shopix.main.mapper;

import hu.shopix.main.dto.PaymentResponse;
import hu.shopix.main.model.Order;
import hu.shopix.main.model.Payment;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class PaymentMapperTest {

    private final PaymentMapper mapper = new PaymentMapper();

    @Test
    void testToResponse() {
        Order order = Order.builder()
                .id(77L)
                .status("NEW")
                .build();

        LocalDateTime created = LocalDateTime.of(2025, 1, 20, 14, 0);

        Payment payment = Payment.builder()
                .id(123L)
                .order(order)
                .status("SUCCESS")
                .providerRef("PAY123-XYZ")
                .createdAt(created)
                .build();

        PaymentResponse resp = mapper.toResponse(payment);

        assertNotNull(resp);
        assertEquals(123L, resp.getId());
        assertEquals(77L, resp.getOrderId());
        assertEquals("SUCCESS", resp.getStatus());
        assertEquals("PAY123-XYZ", resp.getProviderRef());
        assertEquals(created, resp.getCreatedAt());
    }
}

