package ar.edu.um.catalog.domain;

import lombok.*;
import java.time.Instant;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class Professional {
    private Long id;
    private Long categoryId;
    private String firstName;
    private String lastName;
    private Boolean enabled;
    private Instant createdAt;
    private Instant updatedAt;
}
