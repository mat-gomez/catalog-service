package ar.edu.um.catalog.domain;

import jakarta.persistence.*;
import lombok.*;
import java.time.Instant;

@Entity
@Table(name = "catalog_sync_state")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class CatalogSyncState {
    @Id
    private Long id;
    @Column(nullable = false)
    private Long currentVersion;
    private Instant lastSyncedAt;
    private String status;
    private String errorMessage;
}
