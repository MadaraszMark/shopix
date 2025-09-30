package hu.shopix.main.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.server.ResponseStatusException;

import hu.shopix.main.dto.PaymentResponse;
import hu.shopix.main.exception.ResourceNotFoundException;
import hu.shopix.main.mapper.PaymentMapper;
import hu.shopix.main.model.Order;
import hu.shopix.main.model.Payment;
import hu.shopix.main.model.User;
import hu.shopix.main.repository.OrderRepository;
import hu.shopix.main.repository.PaymentRepository;

@ExtendWith(MockitoExtension.class)
class PaymentServiceTest {

    @Mock
    private PaymentRepository paymentRepository;

    @Mock
    private OrderRepository orderRepository;

    @Mock
    private PaymentMapper mapper;

    @InjectMocks
    private PaymentService paymentService;

    private User user;
    private Order order;
    private Payment payment;
    private PaymentResponse response;

    @BeforeEach
    void setup() {
        user = User.builder().id(1L).email("test@test.com").build();
        order = Order.builder().id(100L).user(user).status("NEW").build();
        payment = Payment.builder().id(200L).order(order).status("SUCCESS").providerRef("MOCK-100").build();
        response = PaymentResponse.builder().id(200L).orderId(100L).status("SUCCESS").providerRef("MOCK-100").build();
    }

    @Test
    void testPay_NewPayment() {
        when(orderRepository.findById(100L)).thenReturn(Optional.of(order));
        when(paymentRepository.findByOrderId(100L)).thenReturn(Optional.empty());
        when(paymentRepository.save(any(Payment.class))).thenReturn(payment);
        when(orderRepository.save(order)).thenReturn(order);
        when(mapper.toResponse(payment)).thenReturn(response);

        PaymentResponse result = paymentService.pay(100L, 1L);

        assertNotNull(result);
        assertEquals("SUCCESS", result.getStatus());
        assertEquals("PAID", order.getStatus());

        verify(paymentRepository).save(any(Payment.class));
        verify(orderRepository, times(1)).save(order);
    }

    @Test
    void testPay_ExistingPayment_OrderNotPaidYet() {
        order.setStatus("NEW");

        when(orderRepository.findById(100L)).thenReturn(Optional.of(order));
        when(paymentRepository.findByOrderId(100L)).thenReturn(Optional.of(payment));
        when(orderRepository.save(order)).thenReturn(order);
        when(mapper.toResponse(payment)).thenReturn(response);

        PaymentResponse result = paymentService.pay(100L, 1L);

        assertEquals("SUCCESS", result.getStatus());
        assertEquals("PAID", order.getStatus());
        verify(orderRepository).save(order);
        verify(paymentRepository, never()).save(any());
    }

    @Test
    void testPay_ExistingPayment_OrderAlreadyPaid() {
        order.setStatus("PAID");

        when(orderRepository.findById(100L)).thenReturn(Optional.of(order));
        when(paymentRepository.findByOrderId(100L)).thenReturn(Optional.of(payment));
        when(mapper.toResponse(payment)).thenReturn(response);

        PaymentResponse result = paymentService.pay(100L, 1L);

        assertEquals("SUCCESS", result.getStatus());
        assertEquals("PAID", order.getStatus());
        verify(orderRepository, never()).save(order);
    }

    @Test
    void testPay_OrderNotFound() {
        when(orderRepository.findById(999L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> paymentService.pay(999L, 1L));
    }

    @Test
    void testPay_NotUserOrder() {
        User otherUser = User.builder().id(2L).build();
        order.setUser(otherUser);

        when(orderRepository.findById(100L)).thenReturn(Optional.of(order));

        assertThrows(ResponseStatusException.class, () -> paymentService.pay(100L, 1L));
    }
}

