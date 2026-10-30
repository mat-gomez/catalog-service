package ar.edu.um.catalog.domain;

import lombok.*;
import java.time.Instant;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class ProfessionalCategory {
    private Long id;
    private String name;
    private String description;
    private Boolean enabled;
    private Instant createdAt;
    private Instant updatedAt;
}