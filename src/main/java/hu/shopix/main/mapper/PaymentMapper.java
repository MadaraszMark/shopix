package hu.shopix.main.mapper;

import org.springframework.stereotype.Component;

import hu.shopix.main.dto.PaymentResponse;
import hu.shopix.main.model.Payment;

@Component
public class PaymentMapper {

    public PaymentResponse toResponse(Payment payment) {
        return PaymentResponse.builder()
                .id(payment.getId())
                .orderId(payment.getOrder().getId())
                .status(payment.getStatus())
                .providerRef(payment.getProviderRef())
                .createdAt(payment.getCreatedAt())
                .build();
    }
}

