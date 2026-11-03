package ar.edu.um.catalog.service;

import ar.edu.um.catalog.adapters.out.persistence.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.web.client.RestClient;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CatalogSyncServiceTest {

    @Mock
    private RestClient catedraRestClient;
    @Mock
    private RestClient.RequestHeadersUriSpec requestHeadersUriSpec;
    @Mock
    private RestClient.RequestHeadersSpec requestHeadersSpec;
    @Mock
    private RestClient.ResponseSpec responseSpec;

    @Mock
    private ProfessionalCategoryRepository categoryRepository;
    @Mock
    private ProfessionalRepository professionalRepository;
    @Mock
    private WeeklyScheduleRepository scheduleRepository;
    @Mock
    private CatalogSyncStateRepository syncStateRepository;

    @InjectMocks
    private CatalogSyncService catalogSyncService;

    @Test
    void testExecuteFullSnapshotSyncSuccess() {
        Map<String, Object> categoryMap = Map.of(
                "id", 1L,
                "name", "Cardiologia",
                "description", "Especialidad",
                "enabled", true,
                "createdAt", "2026-01-01T00:00:00Z",
                "updatedAt", "2026-01-01T00:00:00Z"
        );

        Map<String, Object> professionalMap = Map.of(
                "id", 10L,
                "categoryId", 1L,
                "firstName", "Juan",
                "lastName", "Perez",
                "enabled", true,
                "createdAt", "2026-01-01T00:00:00Z",
                "updatedAt", "2026-01-01T00:00:00Z"
        );

        Map<String, Object> scheduleMap = Map.of(
                "id", 100L,
                "professionalId", 10L,
                "dayOfWeek", "MONDAY",
                "startTime", "08:00:00",
                "endTime", "12:00:00",
                "slotDurationMinutes", 30,
                "enabled", true,
                "createdAt", "2026-01-01T00:00:00Z",
                "updatedAt", "2026-01-01T00:00:00Z"
        );

        Map<String, Object> mockSnapshot = Map.of(
                "snapshotVersion", 1,
                "professionalCategories", List.of(categoryMap),
                "professionals", List.of(professionalMap),
                "weeklySchedules", List.of(scheduleMap)
        );

        when(catedraRestClient.get()).thenReturn(requestHeadersUriSpec);
        when(requestHeadersUriSpec.uri(any(String.class))).thenReturn(requestHeadersSpec);
        when(requestHeadersSpec.retrieve()).thenReturn(responseSpec);
        when(responseSpec.body(any(ParameterizedTypeReference.class))).thenReturn(mockSnapshot);

        when(syncStateRepository.findById(1L)).thenReturn(Optional.of(new CatalogSyncStateEntity()));

        assertDoesNotThrow(() -> catalogSyncService.executeFullSnapshotSync());

        verify(scheduleRepository, times(1)).deleteAll();
        verify(professionalRepository, times(1)).deleteAll();
        verify(categoryRepository, times(1)).deleteAll();

        verify(categoryRepository, times(1)).save(any(ProfessionalCategoryEntity.class));
        verify(professionalRepository, times(1)).save(any(ProfessionalEntity.class));
        verify(scheduleRepository, times(1)).save(any(WeeklyScheduleEntity.class));
        verify(syncStateRepository, times(1)).save(any(CatalogSyncStateEntity.class));
    }

    @Test
    void testExecuteFullSnapshotSyncNullResponse() {
        when(catedraRestClient.get()).thenReturn(requestHeadersUriSpec);
        when(requestHeadersUriSpec.uri(any(String.class))).thenReturn(requestHeadersSpec);
        when(requestHeadersSpec.retrieve()).thenReturn(responseSpec);
        when(responseSpec.body(any(ParameterizedTypeReference.class))).thenReturn(null);

        assertThrows(IllegalStateException.class, () -> catalogSyncService.executeFullSnapshotSync());

        verify(scheduleRepository, never()).deleteAll();
        verify(professionalRepository, never()).deleteAll();
        verify(categoryRepository, never()).deleteAll();
    }
}