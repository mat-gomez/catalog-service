package ar.edu.um.catalog.domain.model;

import ar.edu.um.catalog.domain.EstadoSincronizacion;
import org.junit.jupiter.api.Test;
import java.time.Instant;

import static org.junit.jupiter.api.Assertions.*;

class EstadoSincronizacionTest {

    @Test
    void testEstadoSincronizacionModel() {
        Instant now = Instant.now();
        EstadoSincronizacion estado = EstadoSincronizacion.builder()
                .currentVersion(3L)
                .lastSyncedAt(now)
                .status("SYNCED")
                .build();

        assertNotNull(estado);
        assertEquals(3L, estado.getCurrentVersion());
        assertEquals(now, estado.getLastSyncedAt());
        assertEquals("SYNCED", estado.getStatus());
    }
}