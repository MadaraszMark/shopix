package hu.shopix.main.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

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
@Schema(description = "A Termék adatait visszaadó DTO")
public class ProductResponse {
	
	@Schema(example = "101")
    private Long id;
	
	@Schema(example = "10")
	private Long categoryId;

	@Schema(description = "Név", example = "Iphone 15 Pro Max")
    private String name;

    @Schema(description = "Leírás", example = "Apple Iphone 15 Pro Max 256GB tárhellyel")
    private String description;

    @Schema(description = "Ár", example = "399990")
    private BigDecimal price;

    @Schema(description = "Aktív", example = "true")
    private boolean active;
	
	@Schema(description = "Létrehozás időpontja", example = "2025-08-18T10:15:30")
	private LocalDateTime createdAt;
	
	@Schema(description = "Kategória neve", example = "Mobiltelefonok")
    private String categoryName;

    @Schema(description = "Raktárkészlet", example = "10")
    private Integer stock;

}
