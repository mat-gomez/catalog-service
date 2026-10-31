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

import java.time.Instant;
import java.time.LocalTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.*;
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
        ProfessionalCategoryEntity entity = new ProfessionalCategoryEntity();
        entity.setId(1L);
        entity.setName("Cardiologia");
        entity.setDescription("Test");
        entity.setEnabled(true);
        entity.setCreatedAt(Instant.now());
        entity.setUpdatedAt(Instant.now());

        when(categoryRepository.findAll()).thenReturn(List.of(entity));

        ResponseEntity<?> response = catalogController.getAllCategories();
        assertNotNull(response);
    }

    @Test
    void testSearchProfessionals() {
        ProfessionalEntity entity = new ProfessionalEntity();
        entity.setId(10L);
        entity.setCategoryId(1L);
        entity.setFirstName("Juan");
        entity.setLastName("Perez");
        entity.setEnabled(true);
        entity.setCreatedAt(Instant.now());
        entity.setUpdatedAt(Instant.now());

        when(professionalRepository.findByFilters(any(), any(), any())).thenReturn(List.of(entity));

        ResponseEntity<?> response = catalogController.searchProfessionals(1L, "Juan", true);
        assertNotNull(response);
    }

    @Test
    void testGetProfessionalSchedules() {
        WeeklyScheduleEntity entity = new WeeklyScheduleEntity();
        entity.setId(100L);
        entity.setProfessionalId(10L);
        entity.setDayOfWeek("MONDAY");
        entity.setStartTime(LocalTime.of(8, 0));
        entity.setEndTime(LocalTime.of(12, 0));
        entity.setSlotDurationMinutes(30);
        entity.setEnabled(true);
        entity.setCreatedAt(Instant.now());
        entity.setUpdatedAt(Instant.now());

        when(scheduleRepository.findByProfessionalId(anyLong())).thenReturn(List.of(entity));

        ResponseEntity<?> response = catalogController.getProfessionalSchedules(10L);
        assertNotNull(response);
    }
}