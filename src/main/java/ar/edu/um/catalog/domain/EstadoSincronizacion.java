package ar.edu.um.catalog.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.Instant;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EstadoSincronizacion {
    private Long currentVersion;
    private Instant lastSyncedAt;
    private String status;
    private String errorMessage;
}