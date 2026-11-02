package ar.edu.um.catalog.service;

import ar.edu.um.catalog.adapters.out.persistence.CatalogSyncStateEntity;
import ar.edu.um.catalog.adapters.out.persistence.CatalogSyncStateRepository;
import ar.edu.um.catalog.adapters.out.persistence.ProcessedKafkaEventRepository;
import ar.edu.um.catalog.adapters.out.persistence.ProfessionalCategoryEntity;
import ar.edu.um.catalog.adapters.out.persistence.ProfessionalCategoryRepository;
import ar.edu.um.catalog.adapters.out.persistence.ProfessionalEntity;
import ar.edu.um.catalog.adapters.out.persistence.ProfessionalRepository;
import ar.edu.um.catalog.adapters.out.persistence.WeeklyScheduleEntity;
import ar.edu.um.catalog.adapters.out.persistence.WeeklyScheduleRepository;
import ar.edu.um.catalog.adapters.out.redis.CatalogRedisAdapter;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
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

    @Spy
    private ObjectMapper objectMapper = new ObjectMapper().registerModule(new JavaTimeModule());

    @Mock
    private ProfessionalCategoryRepository categoryRepository;

    @Mock
    private ProfessionalRepository professionalRepository;

    @Mock
    private WeeklyScheduleRepository scheduleRepository;

    @InjectMocks
    private CatalogIncrementalSyncService catalogIncrementalSyncService;

    @BeforeEach
    void setUp() {
        if (objectMapper.getRegisteredModuleIds().stream().noneMatch(id -> id.toString().contains("JavaTimeModule"))) {
            objectMapper.registerModule(new JavaTimeModule());
        }
    }

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
        when(redisAdapter.getChangesJson(2L)).thenReturn("{\"version\":2, \"changes\":{\"professionalCategories\":[], \"professionals\":[], \"weeklySchedules\":[]}}");

        catalogIncrementalSyncService.processCatalogUpdate("evt-456", 2L);

        verify(syncStateRepository, times(1)).save(any(CatalogSyncStateEntity.class));
        verify(processedEventRepository, times(1)).save(any());
        verifyNoInteractions(categoryRepository, professionalRepository, scheduleRepository);
    }

    @Test
    void testProcessCatalogUpdate_WithAllEntityMutations() {
        when(processedEventRepository.existsByEventId(anyString())).thenReturn(false);

        CatalogSyncStateEntity existingState = new CatalogSyncStateEntity();
        existingState.setId(1L);
        existingState.setCurrentVersion(1L);

        when(syncStateRepository.findById(1L)).thenReturn(Optional.of(existingState));
        when(redisAdapter.getOldestAvailableVersion()).thenReturn(1L);

        String changesJson = "{\"version\":2, \"changes\":{\"professionalCategories\":[10], \"professionals\":[20], \"weeklySchedules\":[30]}}";
        when(redisAdapter.getChangesJson(2L)).thenReturn(changesJson);

        when(redisAdapter.getEntityJson("professional-categories", 10L)).thenReturn("{\"id\":10, \"name\":\"Cardiología\", \"enabled\":true}");
        when(redisAdapter.getEntityJson("professionals", 20L)).thenReturn("{\"id\":20, \"categoryId\":10, \"firstName\":\"Ana\", \"lastName\":\"Gomez\", \"enabled\":true}");
        when(redisAdapter.getEntityJson("weekly-schedules", 30L)).thenReturn("{\"id\":30, \"professionalId\":20, \"dayOfWeek\":\"MONDAY\", \"startTime\":\"09:00:00\", \"endTime\":\"12:00:00\", \"slotDurationMinutes\":30, \"enabled\":true}");

        catalogIncrementalSyncService.processCatalogUpdate("evt-999", 2L);

        verify(categoryRepository, times(1)).save(any(ProfessionalCategoryEntity.class));
        verify(professionalRepository, times(1)).save(any(ProfessionalEntity.class));
        verify(scheduleRepository, times(1)).save(any(WeeklyScheduleEntity.class));
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

    @Test
    void testProcessCatalogUpdate_MissingChangesJson() {
        when(processedEventRepository.existsByEventId(anyString())).thenReturn(false);

        CatalogSyncStateEntity existingState = new CatalogSyncStateEntity();
        existingState.setId(1L);
        existingState.setCurrentVersion(1L);

        when(syncStateRepository.findById(1L)).thenReturn(Optional.of(existingState));
        when(redisAdapter.getOldestAvailableVersion()).thenReturn(1L);
        when(redisAdapter.getChangesJson(2L)).thenReturn(null);

        catalogIncrementalSyncService.processCatalogUpdate("evt-null-delta", 2L);

        verify(syncStateRepository, times(1)).save(any(CatalogSyncStateEntity.class));
        verify(processedEventRepository, times(1)).save(any());
    }

    @Test
    void testProcessCatalogUpdate_EntityNotFoundInRedis() {
        when(processedEventRepository.existsByEventId(anyString())).thenReturn(false);

        CatalogSyncStateEntity existingState = new CatalogSyncStateEntity();
        existingState.setId(1L);
        existingState.setCurrentVersion(1L);

        when(syncStateRepository.findById(1L)).thenReturn(Optional.of(existingState));
        when(redisAdapter.getOldestAvailableVersion()).thenReturn(1L);

        String changesJson = "{\"version\":2, \"changes\":{\"professionalCategories\":[99], \"professionals\":[], \"weeklySchedules\":[]}}";
        when(redisAdapter.getChangesJson(2L)).thenReturn(changesJson);
        when(redisAdapter.getEntityJson("professional-categories", 99L)).thenReturn(null);

        catalogIncrementalSyncService.processCatalogUpdate("evt-missing-entity", 2L);

        verify(categoryRepository, never()).save(any());
        verify(syncStateRepository, times(1)).save(any());
    }

    @Test
    void testProcessCatalogUpdate_JsonNodeNotAnArray() {
        when(processedEventRepository.existsByEventId(anyString())).thenReturn(false);

        CatalogSyncStateEntity existingState = new CatalogSyncStateEntity();
        existingState.setId(1L);
        existingState.setCurrentVersion(1L);

        when(syncStateRepository.findById(1L)).thenReturn(Optional.of(existingState));
        when(redisAdapter.getOldestAvailableVersion()).thenReturn(1L);

        String changesJson = "{\"version\":2, \"changes\":{\"professionalCategories\":\"not-an-array\", \"professionals\":null, \"weeklySchedules\":[]}}";
        when(redisAdapter.getChangesJson(2L)).thenReturn(changesJson);

        catalogIncrementalSyncService.processCatalogUpdate("evt-not-array", 2L);

        verify(syncStateRepository, times(1)).save(any());
        verify(processedEventRepository, times(1)).save(any());
    }

    @Test
    void testProcessCatalogUpdate_CatchExceptionDuringParsing() {
        when(processedEventRepository.existsByEventId(anyString())).thenReturn(false);

        CatalogSyncStateEntity existingState = new CatalogSyncStateEntity();
        existingState.setId(1L);
        existingState.setCurrentVersion(1L);

        when(syncStateRepository.findById(1L)).thenReturn(Optional.of(existingState));
        when(redisAdapter.getOldestAvailableVersion()).thenReturn(1L);

        String changesJson = "{\"version\":2, \"changes\":{\"professionalCategories\":[10], \"professionals\":[], \"weeklySchedules\":[]}}";
        when(redisAdapter.getChangesJson(2L)).thenReturn(changesJson);

        when(redisAdapter.getEntityJson("professional-categories", 10L)).thenReturn("{invalid-json}");

        try {
            catalogIncrementalSyncService.processCatalogUpdate("evt-exception", 2L);
        } catch (RuntimeException e) {
        }

        verify(processedEventRepository, never()).save(any());
    }
}

