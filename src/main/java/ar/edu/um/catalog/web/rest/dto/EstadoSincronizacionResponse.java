package ar.edu.um.catalog.web.rest.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class EstadoSincronizacionResponse {
    private Long currentVersion;
    private Long oldestAvailableVersion;
    private String status;
}