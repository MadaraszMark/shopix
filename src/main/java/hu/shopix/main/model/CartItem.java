package hu.shopix.main.model;

import java.math.BigDecimal;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "cart_items")
@EntityListeners(AuditingEntityListener.class)
@Getter @Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED) // JPA
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Builder
public class CartItem {
	
	// POJO
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
	private Long id;
	
	@ManyToOne(optional = false)
    @JoinColumn(name = "cart_id", nullable = false)
	private Cart cart;
	
	@ManyToOne(optional = false)
    @JoinColumn(name = "product_id", nullable = false)
	private Product product;
	
	@Min(1)
    @Column(nullable = false)
	private Integer quantity;
	
	@DecimalMin(value = "0.00")
    @Column(name = "unit_price_snapshot", nullable = false, precision = 19, scale = 2)
	private BigDecimal unitPriceSnapshot;

}
