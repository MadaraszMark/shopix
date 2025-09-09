package hu.shopix.main.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "products")
@EntityListeners(AuditingEntityListener.class)
@Getter @Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED) // JPA
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Builder
public class Product {
	
	// POJO

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Long id;

    @NotBlank(message = "A termék neve kötelező!")
    @Size(max = 255)
    @Column(nullable = false, length = 255)
    private String name;

    @Lob
    private String description;

    @NotNull
    @DecimalMin(value = "0.00")
    @Column(nullable = false, precision = 19, scale = 2)
    private BigDecimal price;

    @ManyToOne(optional = false)
    @JoinColumn(name = "category_id", nullable = false)
    private Category category;

    @Column(nullable = false)
    @Builder.Default
    private boolean active = true;

    @CreatedDate
    @Column(name = "created_at", updatable = false, nullable = false)
    private LocalDateTime createdAt;

    @OneToOne(mappedBy = "product", cascade = CascadeType.ALL, orphanRemoval = true)
    private Inventory inventory;
}

