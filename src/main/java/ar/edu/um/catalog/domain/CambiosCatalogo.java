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
public class CambiosCatalogo {
    private Long targetVersion;
    private Instant lastUpdated;
    private Boolean hasChanges;
}