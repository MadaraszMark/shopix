package hu.shopix.main.service;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
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
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CartService {
	
	private final CartRepository cartRepository;
	private final CartMapper mapper;
	private final CartItemRepository cartItemRepository;
	private final ProductRepository productRepository;
	private final UserRepository userRepository;

	@Transactional(readOnly = true) // Optimalizálás
	public CartResponse getOpenCartByUser(Long userId) {
		Cart cart = cartRepository.findFirstByUserIdAndStatus(userId, "OPEN")
				.orElseThrow(() -> new ResourceNotFoundException("Kosár (OPEN)", userId));
		return mapper.toResponseCart(cart);
	}
	
	@Transactional
	public CartResponse addItem(Long userId, AddCartItemRequest request) {
	    if (request.getQuantity() == null || request.getQuantity() <= 0) {
	        throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "A mennyiségnek pozitívnak kell lennie.");
	    }

	    Cart cart = getOrCreateOpenCart(userId);
	    Product product = productRepository.findById(request.getProductId())
	            .orElseThrow(() -> new ResourceNotFoundException("Termék", request.getProductId()));

	    CartItem item = cartItemRepository.findByCartIdAndProductId(cart.getId(), product.getId()).orElse(null);

	    if (item == null) {
	        item = CartItem.builder()
	                .cart(cart)
	                .product(product)
	                .quantity(request.getQuantity())
	                .unitPriceSnapshot(product.getPrice())
	                .build();
	    } else {
	        item.setQuantity(item.getQuantity() + request.getQuantity());
	    }

	    cartItemRepository.save(item);

	    return mapper.toResponseCart(cartRepository.findById(cart.getId()).orElse(cart));
	}

	
	@Transactional
	public CartResponse updateItem(Long userId, CartItemUpdateRequest request) {
		Cart cart = cartRepository.findFirstByUserIdAndStatus(userId, "OPEN")
				.orElseThrow(() -> new ResourceNotFoundException("Kosár", userId));

		CartItem item = cartItemRepository
				.findByCartIdAndProductId(cart.getId(), request.getProductId())
				.orElseThrow(() -> new ResourceNotFoundException("Kosár tétel", request.getProductId()));
		
		if (request.getQuantity() < 0) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "A mennyiség nem lehet negatív.");
		}

		if (request.getQuantity() == 0) {
			cartItemRepository.delete(item);
		} else {
			item.setQuantity(request.getQuantity());
			cartItemRepository.save(item);
		}

		return mapper.toResponseCart(cartRepository.findById(cart.getId()).orElse(cart));
	}
	
	@Transactional
	public CartResponse clearCart(Long userId) {
		cartItemRepository.deleteAllByUserId(userId);
		Cart cart = cartRepository.findFirstByUserIdAndStatus(userId, "OPEN")
				.orElseThrow(() -> new ResourceNotFoundException("Kosár", userId));
		return mapper.toResponseCart(cart);
	}
	
	// -- Segédmetódus -- \\
	private Cart getOrCreateOpenCart(Long userId) {
        return cartRepository.findFirstByUserIdAndStatus(userId, "OPEN").orElseGet(() -> {
            User user = userRepository.findById(userId)
					.orElseThrow(() -> new ResourceNotFoundException("Felhasználó", userId));
            return cartRepository.save(Cart.builder().user(user).status("OPEN").build());
        });
    }
}

