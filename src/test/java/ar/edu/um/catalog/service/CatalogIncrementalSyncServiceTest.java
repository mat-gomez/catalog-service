package ar.edu.um.catalog.service;

import ar.edu.um.catalog.adapters.out.persistence.*;
import ar.edu.um.catalog.adapters.out.redis.CatalogRedisAdapter;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
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

    @Spy
    private ObjectMapper objectMapper = new ObjectMapper();

    @Mock
    private ProfessionalCategoryRepository categoryRepository;
    @Mock
    private ProfessionalRepository professionalRepository;
    @Mock
    private WeeklyScheduleRepository scheduleRepository;

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

        CatalogSyncStateEntity existingState = new CatalogSyncStateEntity();
        existingState.setId(1L);
        existingState.setCurrentVersion(1L);

        when(syncStateRepository.findById(1L)).thenReturn(Optional.of(existingState));
        when(redisAdapter.getOldestAvailableVersion()).thenReturn(1L);

        // Simulamos el JSON de cambios vacío que define la cátedra
        when(redisAdapter.getChangesJson(2L)).thenReturn("{\"version\":2, \"changes\":{\"professionalCategories\":[], \"professionals\":[], \"weeklySchedules\":[]}}");

        catalogIncrementalSyncService.processCatalogUpdate("evt-456", 2L);

        verify(syncStateRepository, times(1)).save(any(CatalogSyncStateEntity.class));
        verify(processedEventRepository, times(1)).save(any());
        verifyNoInteractions(categoryRepository, professionalRepository, scheduleRepository);
    }

    @Test
    void testProcessCatalogUpdate_WithDataMutation() {
        when(processedEventRepository.existsByEventId(anyString())).thenReturn(false);

        CatalogSyncStateEntity existingState = new CatalogSyncStateEntity();
        existingState.setId(1L);
        existingState.setCurrentVersion(1L);

        when(syncStateRepository.findById(1L)).thenReturn(Optional.of(existingState));
        when(redisAdapter.getOldestAvailableVersion()).thenReturn(1L);

        // 1. Simulamos el JSON de cambios que trae la lista de IDs afectados (según contrato oficial)
        String changesJson = "{\"version\":2, \"changes\":{\"professionalCategories\":[10], \"professionals\":[], \"weeklySchedules\":[]}}";
        when(redisAdapter.getChangesJson(2L)).thenReturn(changesJson);

        // 2. Simulamos que cuando el servicio vaya a buscar la entidad al Hash de Redis, este devuelva el JSON completo de la categoría
        String categoryJson = "{\"id\":10, \"name\":\"Cardiología\", \"enabled\":true}";
        when(redisAdapter.getEntityJson(eq("professional-categories"), eq(10L))).thenReturn(categoryJson);

        catalogIncrementalSyncService.processCatalogUpdate("evt-999", 2L);

        // Verificamos que se haya guardado la categoría obtenida desde Redis en PostgreSQL
        verify(categoryRepository, times(1)).save(any(ProfessionalCategoryEntity.class));
        verify(syncStateRepository, times(1)).save(any());
        verify(processedEventRepository, times(1)).save(any());
    }

    @Test
    void testProcessCatalogUpdate_DiscontinuityTriggersFullSync() {
        when(processedEventRepository.existsByEventId(anyString())).thenReturn(false);

        CatalogSyncStateEntity currentState = new CatalogSyncStateEntity();
        currentState.setId(1L);
        currentState.setCurrentVersion(1L);

        when(syncStateRepository.findById(1L)).thenReturn(Optional.of(currentState));
        when(redisAdapter.getOldestAvailableVersion()).thenReturn(5L);

        catalogIncrementalSyncService.processCatalogUpdate("evt-789", 10L);

        verify(fullSyncService, times(1)).executeFullSnapshotSync();
        verify(processedEventRepository, times(1)).save(any());
    }
}