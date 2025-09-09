package hu.shopix.main.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "users", indexes = { @Index(name = "uk_users_email", columnList = "email", unique = true)})
@EntityListeners(AuditingEntityListener.class)
@Getter @Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED) // JPA
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Builder
public class User {
	
	//  POJO

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Long id;

    @Email
    @NotBlank
    @Size(max = 255)
    @Column(nullable = false, unique = true, length = 255)
    private String email;

    @JsonIgnore //JSONE-ba sose küldjük ki
    @NotBlank
    @Column(name = "password_hash", nullable = false, length = 255)
    private String passwordHash;

    @Column(nullable = false, length = 32)
    private String role;

    @CreatedDate
    @Column(name = "created_at", updatable = false, nullable = false)
    private LocalDateTime createdAt;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
    @JsonIgnore // entitásnál elkerüli a ciklust, API-ban inkább DTO
    private List<Address> addresses = new ArrayList<>();
}

