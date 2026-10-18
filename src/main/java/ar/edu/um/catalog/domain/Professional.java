package ar.edu.um.catalog.domain;

import jakarta.persistence.*;
import lombok.*;
import java.time.Instant;

@Entity
@Table(name = "professionals")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class Professional {
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
