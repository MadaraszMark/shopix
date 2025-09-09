package hu.shopix.main.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import lombok.*;

@Entity
@Table(name = "inventory", uniqueConstraints = @UniqueConstraint(name = "uk_inventory_product", columnNames = {"product_id"})) // biztosítja az egyediséget.
@Getter @Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED) // JPA
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Builder
public class Inventory {
	
	// POJO

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Long id;

    @OneToOne(optional = false) // minden termékhez pontosan egy Inventory tartozik.
    @JoinColumn(name = "product_id", nullable = false, unique = true)
    private Product product;

    @Min(0)
    @Column(nullable = false)
    @Builder.Default // Alapból 0 mennyiséget kap.
    private Integer quantity = 0;
}

