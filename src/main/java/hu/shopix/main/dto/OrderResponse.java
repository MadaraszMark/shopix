package hu.shopix.main.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema(description = "A rendelés adatait visszaadó DTO")
public class OrderResponse {
	
	@Schema(description = "Rendelés azonosítója", example = "11")
	private Long id;
	
	@Schema(description = "Rendelés bruttó ára", example = "256000")
	private BigDecimal totalGross;
	
	@Schema(description = "Státusz", example = "OPEN")
	private String status;
	
	@Schema(description = "A rendelés létrehozása", example = "2025-08-18T10:15:30")
	private LocalDateTime createdAt;
	
	@Schema(description = "A rendelésben lévő tételek listája")
	@Builder.Default
	private List<OrderItemResponse> items = new ArrayList<>();

}
