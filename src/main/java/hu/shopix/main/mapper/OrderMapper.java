package hu.shopix.main.mapper;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.stereotype.Component;

import hu.shopix.main.dto.OrderItemResponse;
import hu.shopix.main.dto.OrderResponse;
import hu.shopix.main.model.Order;
import hu.shopix.main.model.OrderItem;

@Component
public class OrderMapper {
	
	public OrderItemResponse toResponseItem(OrderItem item) {
		BigDecimal unitPrice = item.getUnitPrice();
		Integer quantity = item.getQuantity();
		
		return OrderItemResponse.builder()
				.productId(item.getProduct().getId())
				.quantity(quantity)
				.unitPrice(unitPrice)
				.lineTotal(unitPrice.multiply(BigDecimal.valueOf(quantity)))
				.build();
	}
	
	public OrderResponse toResponse(Order order) {
		List<OrderItemResponse> items = order.getItems().stream().map(this::toResponseItem).toList();
		
		return OrderResponse.builder()
				.id(order.getId())
				.totalGross(order.getTotalGross())
				.status(order.getStatus())
				.createdAt(order.getCreatedAt())
				.items(items)
				.build();
	}

}
