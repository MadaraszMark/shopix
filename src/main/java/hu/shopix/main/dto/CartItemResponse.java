package hu.shopix.main.dto;

import java.math.BigDecimal;

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
@Schema(description = "A kosárban lévő termékeket visszaadó DTO")
public class CartItemResponse {
	
	@Schema(description = "Kosár tétel azonosítója", example = "11")
	private Long id;
	
	@Schema(description = "Kosár azonosítója", example = "22")
	private Long cartId;
	
	@Schema(description = "Termék azonosítója", example = "33")
	private Long productId;
	
	@Schema(description = "Mennyiség", example = "2")
	private Integer quantity;
	
	@Schema(description = "Egységár pillanatkép", example = "399990.00")
	private BigDecimal unitPriceSnapshot;

}
