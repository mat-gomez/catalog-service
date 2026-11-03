package ar.edu.um.catalog.config;

import ar.edu.um.catalog.adapters.out.persistence.ProfessionalRepository;
import ar.edu.um.catalog.ports.in.CatalogSyncUseCase;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CatalogInitializerTest {

    @Mock
    private ProfessionalRepository professionalRepository;

    @Mock
    private CatalogSyncUseCase catalogSyncUseCase;

    @InjectMocks
    private CatalogInitializer catalogInitializer;

    @Test
    void testOnApplicationReady_WhenDatabaseIsEmpty_ShouldTriggerSnapshot() {
        when(professionalRepository.count()).thenReturn(0L);
        doNothing().when(catalogSyncUseCase).executeFullSnapshotSync();

        catalogInitializer.onApplicationReady();

        verify(professionalRepository, times(1)).count();
        verify(catalogSyncUseCase, times(1)).executeFullSnapshotSync();
    }

    @Test
    void testOnApplicationReady_WhenDatabaseIsNotEmpty_ShouldNotTriggerSnapshot() {
        when(professionalRepository.count()).thenReturn(5L);

        catalogInitializer.onApplicationReady();

        verify(professionalRepository, times(1)).count();
        verify(catalogSyncUseCase, never()).executeFullSnapshotSync();
    }
}