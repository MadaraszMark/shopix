package hu.shopix.main.model;

import java.time.LocalDateTime;

import org.springframework.data.annotation.CreatedDate;
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
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "addresses")
@EntityListeners(AuditingEntityListener.class)
@Getter @Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED) // JPA
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Builder
public class Address {
	
	// POJO
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@EqualsAndHashCode.Include
	private Long id;
	
	@ManyToOne 
	@JoinColumn(name = "user_id", nullable = false)
	private User user;
	
	@NotBlank(message = "Az utca kötelező!")
	@Size(max = 255)
	private String street;
	
	@NotBlank(message = "A város kötelező!")
	@Size(max = 120)
	private String city;
	
	@NotBlank(message = "Az irányítószám kötelező!")
	@Column(name = "zip_code")
	@Size(max = 32)
	private String zipCode;
	
	@NotBlank(message = "Az ország kötelező!")
	@Size(max = 120)
	private String country;
	
	@CreatedDate
	@Column(name = "created_at", updatable = false, nullable = false)
	private LocalDateTime createdAt;

}
