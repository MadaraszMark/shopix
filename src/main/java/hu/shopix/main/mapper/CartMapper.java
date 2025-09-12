package hu.shopix.main.mapper;

import java.util.List;

import org.springframework.stereotype.Component;

import hu.shopix.main.dto.CartItemResponse;
import hu.shopix.main.dto.CartResponse;
import hu.shopix.main.model.Cart;
import hu.shopix.main.model.CartItem;

@Component
public class CartMapper {
	
	public CartItemResponse toResponseItem(CartItem item) {
		return CartItemResponse.builder()
				.id(item.getId())
				.cartId(item.getCart().getId())
				.productId(item.getProduct().getId())
				.quantity(item.getQuantity())
				.unitPriceSnapshot(item.getUnitPriceSnapshot())
				.build();
	}
	
	public CartResponse toResponseCart(Cart cart) {
		List<CartItemResponse> items = cart.getItems().stream().map(this::toResponseItem).toList();
		
		return CartResponse.builder()
				.id(cart.getId())
				.userId(cart.getUser().getId())
				.status(cart.getStatus())
				.updatedAt(cart.getUpdatedAt())
				.createdAt(cart.getCreatedAt())
				.items(items)
				.build();
	}

}
