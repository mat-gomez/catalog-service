package ar.edu.um.catalog.web.rest;

import ar.edu.um.catalog.service.ConsultarEstadoSincronizacionUseCase;
import ar.edu.um.catalog.domain.EstadoSincronizacion;
import ar.edu.um.catalog.web.rest.dto.EstadoSincronizacionResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/synchronization")
public class EstadoSincronizacionController {

    private final ConsultarEstadoSincronizacionUseCase consultarEstadoSincronizacionUseCase;

    public EstadoSincronizacionController(ConsultarEstadoSincronizacionUseCase consultarEstadoSincronizacionUseCase) {
        this.consultarEstadoSincronizacionUseCase = consultarEstadoSincronizacionUseCase;
    }

    @GetMapping("/status")
    public ResponseEntity<EstadoSincronizacionResponse> getEstadoSincronizacion() {
        EstadoSincronizacion estado = consultarEstadoSincronizacionUseCase.consultarEstado();

        EstadoSincronizacionResponse response = EstadoSincronizacionResponse.builder()
                .currentVersion(estado.getCurrentVersion())
                .oldestAvailableVersion(0L)
                .status(estado.getStatus())
                .build();

        return ResponseEntity.ok(response);
    }
}