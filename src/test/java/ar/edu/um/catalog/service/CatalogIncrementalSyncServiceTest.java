package ar.edu.um.catalog.service;

import ar.edu.um.catalog.adapters.out.persistence.ProfessionalCategoryRepository;
import ar.edu.um.catalog.adapters.out.persistence.ProfessionalRepository;
import ar.edu.um.catalog.adapters.out.persistence.WeeklyScheduleRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@ExtendWith(MockitoExtension.class)
class CatalogIncrementalSyncServiceTest {

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
    void testExecuteIncrementalSync() {

        assertDoesNotThrow(() -> {
        });
    }
}