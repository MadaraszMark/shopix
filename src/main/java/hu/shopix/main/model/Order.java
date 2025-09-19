package hu.shopix.main.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Entity
@Table(name = "orders")
@EntityListeners(AuditingEntityListener.class)
@Getter @Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED) // JPA
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Builder
public class Order {
	
	// POJO

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Long id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @DecimalMin(value = "0.00")
    @Column(name = "total_gross", nullable = false, precision = 19, scale = 2)
    private BigDecimal totalGross;

    @Column(nullable = false, length = 32)
    @NotNull
    private String status;

    @CreatedDate
    @Column(name = "created_at", updatable = false, nullable = false)
    private LocalDateTime createdAt;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
    private List<OrderItem> items = new ArrayList<>();

    @OneToOne(mappedBy = "order", cascade = CascadeType.ALL)
    private Payment payment;
    
    // Snapshot mezők \\
    
    @Column(name = "shipping_street", length = 255)
    private String shippingStreet;

    @Column(name = "shipping_city", length = 120)
    private String shippingCity;

    @Column(name = "shipping_zip", length = 32)
    private String shippingZip;

    @Column(name = "shipping_country", length = 120)
    private String shippingCountry;
    
    @Column(name = "billing_street", length = 255)
    private String billingStreet;

    @Column(name = "billing_city", length = 120)
    private String billingCity;

    @Column(name = "billing_zip", length = 32)
    private String billingZip;

    @Column(name = "billing_country", length = 120)
    private String billingCountry;



}

