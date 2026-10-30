package ar.edu.um.catalog.adapters.out.persistence;

import jakarta.persistence.*;
import lombok.*;
import java.time.Instant;

@Entity
@Table(name = "professional_categories")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class ProfessionalCategoryEntity {
    @Id
    private Long id;
    @Column(nullable = false)
    private String name;
    private String description;
    @Column(nullable = false)
    private Boolean enabled;
    private Instant createdAt;
    private Instant updatedAt;
}