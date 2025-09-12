package hu.shopix.main.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

@Service
public class CartService {
	
	private final CartRepository cartRepository;
	private final CartMapper mapper;
	private final CartItemRepository cartItemRepository;
	private final ProductRepository productRepository;
	private final UserRepository userRepository;
	
	public CartService(CartRepository cartRepository, CartMapper mapper, CartItemRepository cartItemRepository,
			ProductRepository productRepository, UserRepository userRepository) {
		this.cartRepository = cartRepository;
		this.mapper = mapper;
		this.cartItemRepository = cartItemRepository;
		this.productRepository = productRepository;
		this.userRepository = userRepository;
	}

	@Transactional(readOnly = true) // Optimalizálás
	public CartResponse getOpenCartByUser(Long userId) { // Megkeresi az adott user (open) kosarát, ha van -> DTO
		Cart cart = cartRepository.findFirstByUserIdAndStatus(userId, "OPEN").orElseThrow(() -> new ResourceNotFoundException("Kosár (OPEN)", userId));
		return mapper.toResponseCart(cart);
	}
	
	@Transactional
	public CartResponse addItem(Long userId, AddCartItemRequest request) { // Termék hozzáadása
		Cart cart = getOrCreateOpenCart(userId);
		Product product = productRepository.findById(request.getProductId()).orElseThrow(() -> new ResourceNotFoundException("Termék", request.getProductId()));
		
		CartItem item = cartItemRepository.findByCartIdAndProductId(cart.getId(), product.getId()).orElseGet(() -> cartItemRepository.save(CartItem.builder()
				.cart(cart)
				.product(product)
				.quantity(0)
				.unitPriceSnapshot(product.getPrice())
				.build()));
		item.setQuantity(item.getQuantity() + request.getQuantity());
		cartItemRepository.save(item);
		
		return mapper.toResponseCart(cartRepository.findById(cart.getId()).orElse(cart));
	}
	
	@Transactional
	public CartResponse updateItem(Long userId, CartItemUpdateRequest request) { // Termék módosítása
		Cart cart = cartRepository.findFirstByUserIdAndStatus(userId, "OPEN").orElseThrow(() -> new ResourceNotFoundException("Kosár", userId));
		CartItem item = cartItemRepository.findByCartIdAndProductId(cart.getId(), request.getProductId()).orElseThrow(() -> new ResourceNotFoundException("Kosár tétel", request.getProductId()));
		
		if (request.getQuantity() == 0) {
			cartItemRepository.delete(item); // Itt egy hard delete
		}else {
			item.setQuantity(request.getQuantity());
			cartItemRepository.save(item);
		}
		return mapper.toResponseCart(cartRepository.findById(cart.getId()).orElse(cart));
	}
	
	@Transactional
	public CartResponse clearCart(Long userId) { // Termékek törlése
		Cart cart = cartRepository.findFirstByUserIdAndStatus(userId, "OPEN").orElseThrow(() -> new ResourceNotFoundException("Kosár", userId));
		
		cart.getItems().clear(); // Tételek tőrlödnek
		cartRepository.save(cart);
		return mapper.toResponseCart(cart);
	}
	
	// -- Segédmetódus -- \\
	private Cart getOrCreateOpenCart(Long userId) { // Ha a USER-nek van egy OPEN státusza -> azt használjuk
        return cartRepository.findFirstByUserIdAndStatus(userId, "OPEN").orElseGet(() -> {
            User user = userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("Felhasználó", userId));
            return cartRepository.save(Cart.builder().user(user).status("OPEN").build());
        });
    }
}
