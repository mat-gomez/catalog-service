package ar.edu.um.catalog.adapters.out.persistence;

import jakarta.persistence.*;
import lombok.*;
import java.time.Instant;

@Entity
@Table(name = "catalog_sync_state")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class CatalogSyncStateEntity {
    @Id
    private Long id;

    private Long currentVersion;
    private Instant lastSyncedAt;
    private String status;
}