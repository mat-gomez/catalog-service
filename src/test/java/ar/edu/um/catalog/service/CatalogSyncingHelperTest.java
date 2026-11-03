package ar.edu.um.catalog.service;

import ar.edu.um.catalog.adapters.out.persistence.CatalogSyncStateEntity;
import ar.edu.um.catalog.adapters.out.persistence.CatalogSyncStateRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CatalogSyncingHelperTest {

    @Mock
    private CatalogSyncStateRepository syncStateRepository;

    @InjectMocks
    private CatalogSyncingHelper syncingHelper;

    @Test
    void testUpdateSuccessStateExistingEntity() {
        CatalogSyncStateEntity existingState = CatalogSyncStateEntity.builder()
                .id(1L)
                .currentVersion(5L)
                .status("ERROR")
                .errorMessage("Error anterior")
                .build();

        when(syncStateRepository.findById(1L)).thenReturn(Optional.of(existingState));
        when(syncStateRepository.save(any(CatalogSyncStateEntity.class))).thenAnswer(invocation -> invocation.getArgument(0));

        syncingHelper.updateSuccessState(10L);

        ArgumentCaptor<CatalogSyncStateEntity> captor = ArgumentCaptor.forClass(CatalogSyncStateEntity.class);
        verify(syncStateRepository, times(1)).save(captor.capture());

        CatalogSyncStateEntity saved = captor.getValue();
        assertEquals(1L, saved.getId());
        assertEquals(10L, saved.getCurrentVersion());
        assertEquals("SUCCESS", saved.getStatus());
        assertNull(saved.getErrorMessage());
        assertNotNull(saved.getLastSyncedAt());
    }

    @Test
    void testUpdateErrorStateNewEntity() {
        when(syncStateRepository.findById(1L)).thenReturn(Optional.empty());
        when(syncStateRepository.save(any(CatalogSyncStateEntity.class))).thenAnswer(invocation -> invocation.getArgument(0));

        String errorMessage = "Fallo de conexion con la catedra";
        syncingHelper.updateErrorState(errorMessage);

        ArgumentCaptor<CatalogSyncStateEntity> captor = ArgumentCaptor.forClass(CatalogSyncStateEntity.class);
        verify(syncStateRepository, times(1)).save(captor.capture());

        CatalogSyncStateEntity saved = captor.getValue();
        assertEquals(1L, saved.getId());
        assertEquals("ERROR", saved.getStatus());
        assertEquals(errorMessage, saved.getErrorMessage());
        assertNotNull(saved.getLastSyncedAt());
    }

    @Test
    void testUpdateErrorStateTruncatesLongMessage() {
        when(syncStateRepository.findById(1L)).thenReturn(Optional.empty());
        when(syncStateRepository.save(any(CatalogSyncStateEntity.class))).thenAnswer(invocation -> invocation.getArgument(0));

        String longMessage = "A".repeat(1200);

        syncingHelper.updateErrorState(longMessage);

        ArgumentCaptor<CatalogSyncStateEntity> captor = ArgumentCaptor.forClass(CatalogSyncStateEntity.class);
        verify(syncStateRepository, times(1)).save(captor.capture());

        CatalogSyncStateEntity saved = captor.getValue();
        assertEquals("ERROR", saved.getStatus());
        assertEquals(1000, saved.getErrorMessage().length());
    }
}