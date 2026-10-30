package ar.edu.um.catalog.controller;

import ar.edu.um.catalog.adapters.in.rest.CatalogController;
import ar.edu.um.catalog.adapters.out.persistence.*;
import ar.edu.um.catalog.ports.in.CatalogSyncUseCase;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CatalogControllerTest {

    @Mock
    private ProfessionalCategoryRepository categoryRepository;
    @Mock
    private ProfessionalRepository professionalRepository;
    @Mock
    private WeeklyScheduleRepository scheduleRepository;
    @Mock
    private CatalogSyncUseCase catalogSyncUseCase;

    @InjectMocks
    private CatalogController catalogController;

    @Test
    void testTriggerSync() {
        doNothing().when(catalogSyncUseCase).executeFullSnapshotSync();
        ResponseEntity<String> response = catalogController.triggerSync();
        assertNotNull(response);
    }

    @Test
    void testGetAllCategories() {
        when(categoryRepository.findAll()).thenReturn(Collections.emptyList());
        ResponseEntity<?> response = catalogController.getAllCategories();
        assertNotNull(response);
    }

    @Test
    void testSearchProfessionals() {
        when(professionalRepository.findByFilters(any(), any(), any())).thenReturn(Collections.emptyList());
        ResponseEntity<?> response = catalogController.searchProfessionals(1L, "Test", true);
        assertNotNull(response);
    }

    @Test
    void testGetProfessionalSchedules() {
        when(scheduleRepository.findByProfessionalId(anyLong())).thenReturn(Collections.emptyList());
        ResponseEntity<?> response = catalogController.getProfessionalSchedules(1L);
        assertNotNull(response);
    }
}