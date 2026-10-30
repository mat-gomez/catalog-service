package ar.edu.um.catalog.service;

import ar.edu.um.catalog.adapters.out.persistence.CatalogSyncStateEntity;
import ar.edu.um.catalog.adapters.out.persistence.CatalogSyncStateRepository;
import ar.edu.um.catalog.adapters.out.persistence.ProcessedKafkaEventRepository;
import ar.edu.um.catalog.adapters.out.redis.CatalogRedisAdapter;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CatalogIncrementalSyncServiceTest {

    @Mock
    private CatalogRedisAdapter redisAdapter;
    @Mock
    private ProcessedKafkaEventRepository processedEventRepository;
    @Mock
    private CatalogSyncStateRepository syncStateRepository;
    @Mock
    private CatalogSyncService fullSyncService;

    @InjectMocks
    private CatalogIncrementalSyncService catalogIncrementalSyncService;

    @Test
    void testServiceInstantiation() {
        assertNotNull(catalogIncrementalSyncService);
    }

    @Test
    void testProcessCatalogUpdate_DuplicateEvent() {
        when(processedEventRepository.existsByEventId("evt-123")).thenReturn(true);

        catalogIncrementalSyncService.processCatalogUpdate("evt-123", 2L);

        verify(processedEventRepository, times(1)).existsByEventId("evt-123");
        verifyNoInteractions(syncStateRepository);
    }

    @Test
    void testProcessCatalogUpdate_NormalUpdate() {
        when(processedEventRepository.existsByEventId(anyString())).thenReturn(false);

        // Simulamos un estado existente con versión 1 para que al recibir versión 2 haga la actualización normal
        CatalogSyncStateEntity existingState = new CatalogSyncStateEntity();
        existingState.setId(1L);
        existingState.setCurrentVersion(1L);

        when(syncStateRepository.findById(1L)).thenReturn(Optional.of(existingState));
        when(redisAdapter.getOldestAvailableVersion()).thenReturn(1L); // localVersion (1) >= oldestAvailable (1), sin discontinuidad

        catalogIncrementalSyncService.processCatalogUpdate("evt-456", 2L);

        verify(syncStateRepository, times(1)).save(any(CatalogSyncStateEntity.class));
        verify(processedEventRepository, times(1)).save(any());
    }

    @Test
    void testProcessCatalogUpdate_DiscontinuityTriggersFullSync() {
        when(processedEventRepository.existsByEventId(anyString())).thenReturn(false);

        CatalogSyncStateEntity currentState = new CatalogSyncStateEntity();
        currentState.setId(1L);
        currentState.setCurrentVersion(1L);

        when(syncStateRepository.findById(1L)).thenReturn(Optional.of(currentState));
        when(redisAdapter.getOldestAvailableVersion()).thenReturn(5L); // localVersion (1) < oldestAvailable (5)

        catalogIncrementalSyncService.processCatalogUpdate("evt-789", 10L);

        verify(fullSyncService, times(1)).executeFullSnapshotSync();
        verify(processedEventRepository, times(1)).save(any());
    }
}