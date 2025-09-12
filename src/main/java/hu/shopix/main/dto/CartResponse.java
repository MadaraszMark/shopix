package hu.shopix.main.dto;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema(description = "A kosár teljes tartalmát visszaadó DTO")
public class CartResponse {
	
	@Schema(description = "Kosár azonosítója", example = "11")
	private Long id;
	
	@Schema(description = "Felhasználó azonosítója", example = "22")
	private Long userId;
	
	@Schema(description = "Státusz", example = "OPEN")
	private String status;
	
	@Schema(description = "A kosár utolsó módosítása", example = "2025-08-18T10:15:30")
	private LocalDateTime updatedAt;
	
	@Schema(description = "A kosár létrehozása", example = "2025-08-18T10:15:30")
	private LocalDateTime createdAt;

	@Schema(description = "A kosárban lévő tételek listája")
	@Builder.Default
	private List<CartItemResponse> items = new ArrayList<>();
}

