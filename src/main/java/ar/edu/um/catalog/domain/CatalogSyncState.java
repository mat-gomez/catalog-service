package ar.edu.um.catalog.domain;

import lombok.*;
import java.time.Instant;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class CatalogSyncState {
    private Long id;
    private Long currentVersion;
    private Instant lastSyncedAt;
    private String status;
    private String errorMessage;
}
