package ar.edu.um.catalog.service;

import ar.edu.um.catalog.adapters.out.persistence.CatalogSyncStateEntity;
import ar.edu.um.catalog.adapters.out.persistence.CatalogSyncStateRepository;
import ar.edu.um.catalog.domain.EstadoSincronizacion;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ConsultarEstadoSincronizacionUseCase {

    private final CatalogSyncStateRepository syncStateRepository;

    public ConsultarEstadoSincronizacionUseCase(CatalogSyncStateRepository syncStateRepository) {
        this.syncStateRepository = syncStateRepository;
    }

    public EstadoSincronizacion consultarEstado() {
        Optional<CatalogSyncStateEntity> entityOpt = syncStateRepository.findById(1L);
        if (entityOpt.isPresent()) {
            CatalogSyncStateEntity e = entityOpt.get();
            return EstadoSincronizacion.builder()
                    .currentVersion(e.getCurrentVersion())
                    .lastSyncedAt(e.getLastSyncedAt())
                    .status(e.getStatus())
                    .build();
        }
        return EstadoSincronizacion.builder()
                .currentVersion(0L)
                .status("NOT_SYNCED")
                .build();
    }
}