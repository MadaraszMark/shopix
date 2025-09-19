package hu.shopix.main.service;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import hu.shopix.main.dto.CreateOrderRequest;
import hu.shopix.main.dto.OrderResponse;
import hu.shopix.main.exception.ResourceNotFoundException;
import hu.shopix.main.mapper.OrderMapper;
import hu.shopix.main.model.Address;
import hu.shopix.main.model.Cart;
import hu.shopix.main.model.CartItem;
import hu.shopix.main.model.Order;
import hu.shopix.main.model.OrderItem;
import hu.shopix.main.model.User;
import hu.shopix.main.repository.AddressRepository;
import hu.shopix.main.repository.CartItemRepository;
import hu.shopix.main.repository.CartRepository;
import hu.shopix.main.repository.OrderItemRepository;
import hu.shopix.main.repository.OrderRepository;
import hu.shopix.main.repository.UserRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final OrderItemRepository orderItemRepository;
    private final CartRepository cartRepository;
    private final CartItemRepository cartItemRepository;
    private final UserRepository userRepository;
    private final AddressRepository addressRepository;
    private final OrderMapper mapper;

    @Transactional
    public OrderResponse createFromCart(Long userId, CreateOrderRequest request) {
        User user = userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("Felhasználó", userId));

        Cart cart = cartRepository.findFirstByUserIdAndStatus(userId, "OPEN").orElseThrow(() -> new ResourceNotFoundException("Kosár (OPEN)", userId));

        List<CartItem> cartItems = cart.getItems();
        if (cartItems == null || cartItems.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "A kosár üres, nem hozható létre rendelés.");
        }

        Address shipping = null;
        Address billing = null;

        if (request != null) {
            if (request.getShippingAddressId() != null) {
                shipping = addressRepository.findById(request.getShippingAddressId()).orElseThrow(() -> new ResourceNotFoundException("Szállítási cím", request.getShippingAddressId()));

                if (!shipping.getUser().getId().equals(userId)) {
                    throw new ResponseStatusException(HttpStatus.FORBIDDEN, "A szállítási cím nem a tiéd.");
                }
            }

            if (request.getBillingAddressId() != null) {
                billing = addressRepository.findById(request.getBillingAddressId()).orElseThrow(() -> new ResourceNotFoundException("Számlázási cím", request.getBillingAddressId()));

                if (!billing.getUser().getId().equals(userId)) {
                    throw new ResponseStatusException(HttpStatus.FORBIDDEN, "A számlázási cím nem a tiéd.");
                }
            }
        }

        if (billing == null) {
            billing = shipping;
        }

        Order order = Order.builder()
                .user(user)
                .status("CREATED")
                .totalGross(BigDecimal.ZERO)
                .build();
        
        if (shipping != null) {
            order.setShippingStreet(shipping.getStreet());
            order.setShippingCity(shipping.getCity());
            order.setShippingZip(shipping.getZipCode());
            order.setShippingCountry(shipping.getCountry());
        }
        if (billing != null) {
            order.setBillingStreet(billing.getStreet());
            order.setBillingCity(billing.getCity());
            order.setBillingZip(billing.getZipCode());
            order.setBillingCountry(billing.getCountry());
        }

        BigDecimal total = BigDecimal.ZERO;
        for (CartItem ci : cartItems) {
            OrderItem oi = OrderItem.builder()
                    .order(order)
                    .product(ci.getProduct())
                    .quantity(ci.getQuantity())
                    .unitPrice(ci.getUnitPriceSnapshot())
                    .build();

            order.getItems().add(oi);

            BigDecimal line = oi.getUnitPrice().multiply(BigDecimal.valueOf(oi.getQuantity()));
            total = total.add(line);
        }
        order.setTotalGross(total);

        Order saved = orderRepository.save(order);

        cart.setStatus("CLOSED");
        cartRepository.save(cart);

        return mapper.toResponse(saved);
    }


    @Transactional(readOnly = true)
    public Page<OrderResponse> listMyOrders(Long userId, Pageable pageable) {
        Page<Order> page = orderRepository.findByUserId(userId, pageable);
        return page.map(mapper::toResponse);
    }

    @Transactional(readOnly = true)
    public OrderResponse getByIdForUser(Long orderId, Long userId) {
        Order order = orderRepository.findById(orderId)
                .filter(o -> o.getUser() != null && o.getUser().getId().equals(userId))
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.FORBIDDEN, "Nem férhetsz hozzá ehhez a rendeléshez."));

        return mapper.toResponse(order);
    }
}

