package ar.edu.um.catalog.service;

import ar.edu.um.catalog.adapters.out.persistence.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.web.client.RestClient;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

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
        Map<String, Object> mockSnapshot = new HashMap<>();
        mockSnapshot.put("snapshotVersion", 1);
        mockSnapshot.put("professionalCategories", Collections.emptyList());
        mockSnapshot.put("professionals", Collections.emptyList());
        mockSnapshot.put("weeklySchedules", Collections.emptyList());

        when(catedraRestClient.get()).thenReturn(requestHeadersUriSpec);
        when(requestHeadersUriSpec.uri(any(String.class))).thenReturn(requestHeadersSpec);
        when(requestHeadersSpec.retrieve()).thenReturn(responseSpec);
        when(responseSpec.body(any(ParameterizedTypeReference.class))).thenReturn(mockSnapshot);

        when(syncStateRepository.findById(1L)).thenReturn(java.util.Optional.empty());

        assertDoesNotThrow(() -> catalogSyncService.executeFullSnapshotSync());

        verify(syncStateRepository, times(1)).save(any(CatalogSyncStateEntity.class));
    }
}