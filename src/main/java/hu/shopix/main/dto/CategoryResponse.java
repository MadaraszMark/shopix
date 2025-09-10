package hu.shopix.main.dto;

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
@Schema(description = "A kategória adatait visszaadó DTO")
public class CategoryResponse {
	
	@Schema(description = "A kategória azonosítója", example = "42")
	private Long id;
	
	@Schema(description = "A kategória neve", example = "TV-k")
	private String name;
	
	@Schema(description = "A kategória szülő azonosítója", example = "1")
	private Long parent;
	
	@Schema(description = "Szülő kategória neve (ha van)", example = "Elektronika")
    private String parentName;

}
