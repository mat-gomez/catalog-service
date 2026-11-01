package ar.edu.um.catalog.service;

import ar.edu.um.catalog.adapters.out.persistence.CatalogSyncStateEntity;
import ar.edu.um.catalog.adapters.out.persistence.CatalogSyncStateRepository;
import ar.edu.um.catalog.domain.EstadoSincronizacion;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.Instant;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ConsultarEstadoSincronizacionUseCaseTest {

    @Mock
    private CatalogSyncStateRepository syncStateRepository;

    @InjectMocks
    private ConsultarEstadoSincronizacionUseCase useCase;

    @Test
    void testConsultarEstadoFound() {
        Instant now = Instant.now();
        CatalogSyncStateEntity entity = CatalogSyncStateEntity.builder()
                .id(1L)
                .currentVersion(10L)
                .lastSyncedAt(now)
                .status("SUCCESS")
                .build();

        when(syncStateRepository.findById(1L)).thenReturn(Optional.of(entity));

        EstadoSincronizacion resultado = useCase.consultarEstado();

        assertNotNull(resultado);
        assertEquals(10L, resultado.getCurrentVersion());
        assertEquals("SUCCESS", resultado.getStatus());
        assertEquals(now, resultado.getLastSyncedAt());
    }

    @Test
    void testConsultarEstadoNotFound() {
        when(syncStateRepository.findById(1L)).thenReturn(Optional.empty());

        EstadoSincronizacion resultado = useCase.consultarEstado();

        assertNotNull(resultado);
        assertEquals(0L, resultado.getCurrentVersion());
        assertEquals("NOT_SYNCED", resultado.getStatus());
    }
}