package ar.edu.um.catalog.service;

import ar.edu.um.catalog.adapters.out.persistence.CatalogSyncStateEntity;
import ar.edu.um.catalog.adapters.out.persistence.CatalogSyncStateRepository;
import ar.edu.um.catalog.domain.SnapshotCatalogo;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;

@Service
public class AplicarSnapshotCatalogoUseCase {

    private final CatalogSyncStateRepository syncStateRepository;

    public AplicarSnapshotCatalogoUseCase(CatalogSyncStateRepository syncStateRepository) {
        this.syncStateRepository = syncStateRepository;
    }

    @Transactional
    public void aplicarSnapshot(SnapshotCatalogo snapshot) {
        if (snapshot == null || snapshot.getVersion() == null) {
            throw new IllegalArgumentException("Snapshot o versión inválida");
        }

        CatalogSyncStateEntity state = CatalogSyncStateEntity.builder()
                .id(1L)
                .currentVersion(snapshot.getVersion())
                .lastSyncedAt(snapshot.getTimestamp() != null ? snapshot.getTimestamp() : Instant.now())
                .status("APPLIED")
                .build();

        syncStateRepository.save(state);
    }
}