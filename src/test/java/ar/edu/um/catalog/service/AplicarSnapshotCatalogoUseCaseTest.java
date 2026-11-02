package ar.edu.um.catalog.service;

import ar.edu.um.catalog.adapters.out.persistence.CatalogSyncStateEntity;
import ar.edu.um.catalog.adapters.out.persistence.CatalogSyncStateRepository;
import ar.edu.um.catalog.domain.SnapshotCatalogo;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.Instant;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AplicarSnapshotCatalogoUseCaseTest {

    @Mock
    private CatalogSyncStateRepository syncStateRepository;

    @InjectMocks
    private AplicarSnapshotCatalogoUseCase useCase;

    @Test
    void testAplicarSnapshotSuccess() {
        SnapshotCatalogo snapshot = SnapshotCatalogo.builder()
                .version(5L)
                .timestamp(Instant.now())
                .build();

        useCase.aplicarSnapshot(snapshot);

        ArgumentCaptor<CatalogSyncStateEntity> captor = ArgumentCaptor.forClass(CatalogSyncStateEntity.class);
        verify(syncStateRepository, times(1)).save(captor.capture());
        assertEquals(5L, captor.getValue().getCurrentVersion());
        assertEquals("APPLIED", captor.getValue().getStatus());
    }

    @Test
    void testAplicarSnapshotNullThrowsException() {
        assertThrows(IllegalArgumentException.class, () -> useCase.aplicarSnapshot(null));
    }
}