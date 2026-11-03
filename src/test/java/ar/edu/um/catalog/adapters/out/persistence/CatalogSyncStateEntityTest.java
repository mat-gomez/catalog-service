package ar.edu.um.catalog.adapters.out.persistence;

import org.junit.jupiter.api.Test;
import java.time.Instant;

import static org.junit.jupiter.api.Assertions.*;

class CatalogSyncStateEntityTest {

    @Test
    void testGettersAndSetters() {
        CatalogSyncStateEntity entity = new CatalogSyncStateEntity();
        Long id = 1L;
        Long currentVersion = 10L;
        Instant now = Instant.now();
        String status = "SYNCED";
        String errorMessage = "Error de prueba";

        entity.setId(id);
        entity.setCurrentVersion(currentVersion);
        entity.setLastSyncedAt(now);
        entity.setStatus(status);
        entity.setErrorMessage(errorMessage);

        assertEquals(id, entity.getId());
        assertEquals(currentVersion, entity.getCurrentVersion());
        assertEquals(now, entity.getLastSyncedAt());
        assertEquals(status, entity.getStatus());
        assertEquals(errorMessage, entity.getErrorMessage());
    }

    @Test
    void testAllArgsConstructor() {
        Instant now = Instant.now();
        CatalogSyncStateEntity entity = new CatalogSyncStateEntity(1L, 5L, now, "PENDING", null);

        assertNotNull(entity);
        assertEquals(1L, entity.getId());
        assertEquals(5L, entity.getCurrentVersion());
        assertEquals(now, entity.getLastSyncedAt());
        assertEquals("PENDING", entity.getStatus());
        assertNull(entity.getErrorMessage());
    }

    @Test
    void testBuilder() {
        Instant now = Instant.now();
        CatalogSyncStateEntity entity = CatalogSyncStateEntity.builder()
                .id(2L)
                .currentVersion(20L)
                .lastSyncedAt(now)
                .status("ERROR")
                .errorMessage("Fallo de conexion con catedra")
                .build();

        assertNotNull(entity);
        assertEquals(2L, entity.getId());
        assertEquals(20L, entity.getCurrentVersion());
        assertEquals(now, entity.getLastSyncedAt());
        assertEquals("ERROR", entity.getStatus());
        assertEquals("Fallo de conexion con catedra", entity.getErrorMessage());
    }
}