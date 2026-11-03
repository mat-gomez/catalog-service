package ar.edu.um.catalog.service;

import ar.edu.um.catalog.adapters.out.persistence.CatalogSyncStateEntity;
import ar.edu.um.catalog.adapters.out.persistence.CatalogSyncStateRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;

@Component
@RequiredArgsConstructor
public class CatalogSyncingHelper {

    private final CatalogSyncStateRepository syncStateRepository;

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void updateSuccessState(Long snapshotVersion) {
        CatalogSyncStateEntity state = syncStateRepository.findById(1L).orElseGet(CatalogSyncStateEntity::new);
        state.setId(1L);
        state.setCurrentVersion(snapshotVersion);
        state.setLastSyncedAt(Instant.now());
        state.setStatus("SUCCESS");
        state.setErrorMessage(null);
        syncStateRepository.save(state);
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void updateErrorState(String errorMessage) {
        CatalogSyncStateEntity state = syncStateRepository.findById(1L).orElseGet(CatalogSyncStateEntity::new);
        state.setId(1L);
        state.setLastSyncedAt(Instant.now());
        state.setStatus("ERROR");
        state.setErrorMessage(formatErrorMessage(errorMessage));
        syncStateRepository.save(state);
    }

    private String formatErrorMessage(String errorMessage) {
        if (errorMessage == null) {
            return "Error desconocido";
        }
        return errorMessage.length() > 1000 ? errorMessage.substring(0, 1000) : errorMessage;
    }
}