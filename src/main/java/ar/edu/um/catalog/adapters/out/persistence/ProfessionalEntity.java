package ar.edu.um.catalog.adapters.out.persistence;

import jakarta.persistence.*;
import lombok.*;
import java.time.Instant;

@Entity
@Table(name = "professionals")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class ProfessionalEntity {
    @Id
    private Long id;
    @Column(nullable = false)
    private Long categoryId;
    @Column(nullable = false)
    private String firstName;
    @Column(nullable = false)
    private String lastName;
    @Column(nullable = false)
    private Boolean enabled;
    private Instant createdAt;
    private Instant updatedAt;
}