package hu.shopix.main.service;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import hu.shopix.main.dto.PaymentResponse;
import hu.shopix.main.exception.ResourceNotFoundException;
import hu.shopix.main.mapper.PaymentMapper;
import hu.shopix.main.model.Order;
import hu.shopix.main.model.Payment;
import hu.shopix.main.repository.OrderRepository;
import hu.shopix.main.repository.PaymentRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PaymentService {

    private final PaymentRepository paymentRepository;
    private final OrderRepository orderRepository;
    private final PaymentMapper mapper;

    @Transactional
    public PaymentResponse pay(Long orderId, Long userId) {
        Order order = orderRepository.findById(orderId).orElseThrow(() -> new ResourceNotFoundException("Rendelés", orderId));

        if (!order.getUser().getId().equals(userId)) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Nem a te rendelésed.");
        }

        return paymentRepository.findByOrderId(orderId).map(existing -> {
                    if (!"PAID".equals(order.getStatus())) {
                        order.setStatus("PAID");
                        orderRepository.save(order);
                    }
                    return mapper.toResponse(existing);
                })
                .orElseGet(() -> {
                    Payment payment = Payment.builder()
                            .order(order)
                            .status("SUCCESS")
                            .providerRef("MOCK-" + orderId)
                            .build();

                    Payment saved = paymentRepository.save(payment);

                    order.setPayment(saved);
                    order.setStatus("PAID");
                    orderRepository.save(order);

                    return mapper.toResponse(saved);
                });

    }
}

