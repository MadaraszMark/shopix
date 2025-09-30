package hu.shopix.main.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.server.ResponseStatusException;

import hu.shopix.main.dto.AddCartItemRequest;
import hu.shopix.main.dto.CartItemUpdateRequest;
import hu.shopix.main.dto.CartResponse;
import hu.shopix.main.exception.ResourceNotFoundException;
import hu.shopix.main.mapper.CartMapper;
import hu.shopix.main.model.Cart;
import hu.shopix.main.model.CartItem;
import hu.shopix.main.model.Product;
import hu.shopix.main.model.User;
import hu.shopix.main.repository.CartItemRepository;
import hu.shopix.main.repository.CartRepository;
import hu.shopix.main.repository.ProductRepository;
import hu.shopix.main.repository.UserRepository;

@ExtendWith(MockitoExtension.class)
class CartServiceTest {

    @Mock
    private CartRepository cartRepository;

    @Mock
    private CartMapper mapper;

    @Mock
    private CartItemRepository cartItemRepository;

    @Mock
    private ProductRepository productRepository;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private CartService cartService;

    private User user;
    private Cart cart;
    private Product product;
    private CartItem item;
    private CartResponse cartResponse;

    @BeforeEach
    void setup() {
        user = User.builder().id(1L).email("test@test.com").build();
        cart = Cart.builder().id(100L).user(user).status("OPEN").build();
        product = Product.builder().id(200L).name("Laptop").price(BigDecimal.valueOf(500)).build();
        item = CartItem.builder().id(300L).cart(cart).product(product).quantity(2).unitPriceSnapshot(product.getPrice()).build();
        cartResponse = CartResponse.builder().id(100L).userId(1L).build();
    }

    @Test
    void testGetOpenCartByUser_Found() {
        when(cartRepository.findFirstByUserIdAndStatus(1L, "OPEN")).thenReturn(Optional.of(cart));
        when(mapper.toResponseCart(cart)).thenReturn(cartResponse);

        CartResponse result = cartService.getOpenCartByUser(1L);

        assertEquals(100L, result.getId());
        verify(cartRepository).findFirstByUserIdAndStatus(1L, "OPEN");
    }

    @Test
    void testGetOpenCartByUser_NotFound() {
        when(cartRepository.findFirstByUserIdAndStatus(1L, "OPEN")).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> cartService.getOpenCartByUser(1L));
    }

    @Test
    void testAddItem_NewItem() {
        AddCartItemRequest request = AddCartItemRequest.builder().productId(200L).quantity(2).build();

        when(cartRepository.findFirstByUserIdAndStatus(1L, "OPEN")).thenReturn(Optional.of(cart));
        when(productRepository.findById(200L)).thenReturn(Optional.of(product));
        when(cartItemRepository.findByCartIdAndProductId(cart.getId(), product.getId())).thenReturn(Optional.empty());
        when(cartRepository.findById(cart.getId())).thenReturn(Optional.of(cart));
        when(mapper.toResponseCart(cart)).thenReturn(cartResponse);

        CartResponse result = cartService.addItem(1L, request);

        assertNotNull(result);
        verify(cartItemRepository).save(any(CartItem.class));
    }

    @Test
    void testAddItem_ExistingItem() {
        AddCartItemRequest request = AddCartItemRequest.builder().productId(200L).quantity(1).build();

        when(cartRepository.findFirstByUserIdAndStatus(1L, "OPEN")).thenReturn(Optional.of(cart));
        when(productRepository.findById(200L)).thenReturn(Optional.of(product));
        when(cartItemRepository.findByCartIdAndProductId(cart.getId(), product.getId())).thenReturn(Optional.of(item));
        when(cartRepository.findById(cart.getId())).thenReturn(Optional.of(cart));
        when(mapper.toResponseCart(cart)).thenReturn(cartResponse);

        CartResponse result = cartService.addItem(1L, request);

        assertNotNull(result);
        assertEquals(3, item.getQuantity());
    }

    @Test
    void testAddItem_InvalidQuantity() {
        AddCartItemRequest request = AddCartItemRequest.builder().productId(200L).quantity(0).build();

        assertThrows(ResponseStatusException.class, () -> cartService.addItem(1L, request));
    }

    @Test
    void testUpdateItem_SetQuantity() {
        CartItemUpdateRequest request = CartItemUpdateRequest.builder().productId(200L).quantity(5).build();

        when(cartRepository.findFirstByUserIdAndStatus(1L, "OPEN")).thenReturn(Optional.of(cart));
        when(cartItemRepository.findByCartIdAndProductId(cart.getId(), 200L)).thenReturn(Optional.of(item));
        when(cartRepository.findById(cart.getId())).thenReturn(Optional.of(cart));
        when(mapper.toResponseCart(cart)).thenReturn(cartResponse);

        CartResponse result = cartService.updateItem(1L, request);

        assertEquals(5, item.getQuantity());
        assertNotNull(result);
    }

    @Test
    void testUpdateItem_DeleteWhenQuantityZero() {
        CartItemUpdateRequest request = CartItemUpdateRequest.builder().productId(200L).quantity(0).build();

        when(cartRepository.findFirstByUserIdAndStatus(1L, "OPEN")).thenReturn(Optional.of(cart));
        when(cartItemRepository.findByCartIdAndProductId(cart.getId(), 200L)).thenReturn(Optional.of(item));
        when(cartRepository.findById(cart.getId())).thenReturn(Optional.of(cart));
        when(mapper.toResponseCart(cart)).thenReturn(cartResponse);

        cartService.updateItem(1L, request);

        verify(cartItemRepository).delete(item);
    }

    @Test
    void testUpdateItem_NegativeQuantity() {
        CartItemUpdateRequest request = CartItemUpdateRequest.builder().productId(200L).quantity(-1).build();

        when(cartRepository.findFirstByUserIdAndStatus(1L, "OPEN")).thenReturn(Optional.of(cart));
        when(cartItemRepository.findByCartIdAndProductId(cart.getId(), 200L)).thenReturn(Optional.of(item));

        assertThrows(ResponseStatusException.class, () -> cartService.updateItem(1L, request));
    }

    @Test
    void testClearCart() {
        when(cartRepository.findFirstByUserIdAndStatus(1L, "OPEN")).thenReturn(Optional.of(cart));
        when(mapper.toResponseCart(cart)).thenReturn(cartResponse);

        CartResponse result = cartService.clearCart(1L);

        assertNotNull(result);
        verify(cartItemRepository).deleteAllByUserId(1L);
    }
}

