package hu.shopix.main.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

@Entity
@Table(name = "categories")
@Getter @Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED) // JPA
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Builder
public class Category {
	
	// POJO

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Long id;

    @NotBlank(message = "A kategórianév kötelező!")
    @Size(max = 120)
    @Column(nullable = false, unique = true, length = 160)
    private String name;

    @ManyToOne
    @JoinColumn(name = "parent_id")
    private Category parent;
}

