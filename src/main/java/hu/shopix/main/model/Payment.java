package hu.shopix.main.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Entity
@Table(name = "payments")
@EntityListeners(AuditingEntityListener.class)
@Getter @Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED) // JPA
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Builder
public class Payment {
	
	// POJO

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Long id;

    @OneToOne
    @JoinColumn(name = "order_id", nullable = false, unique = true)
    private Order order;

    @Column(nullable = false, length = 32)
    private String status;

    @Column(name = "provider_ref")
    private String providerRef;

    @Column(name = "created_at", updatable = false, nullable = false)
    @CreatedDate
    private LocalDateTime createdAt;

}

