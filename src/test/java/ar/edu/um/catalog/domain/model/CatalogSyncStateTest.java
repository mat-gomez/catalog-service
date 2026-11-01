package ar.edu.um.catalog.domain.model;

import ar.edu.um.catalog.domain.CatalogSyncState;
import org.junit.jupiter.api.Test;
import java.time.Instant;

import static org.junit.jupiter.api.Assertions.*;

class CatalogSyncStateTest {

    @Test
    void testCatalogSyncStateGettersAndSetters() {
        CatalogSyncState state = new CatalogSyncState();

        Long id = 1L;
        Long currentVersion = 5L;
        Instant now = Instant.now();
        String status = "SUCCESS";
        String errorMessage = null;

        state.setId(id);
        state.setCurrentVersion(currentVersion);
        state.setLastSyncedAt(now);
        state.setStatus(status);
        state.setErrorMessage(errorMessage);

        assertEquals(id, state.getId());
        assertEquals(currentVersion, state.getCurrentVersion());
        assertEquals(now, state.getLastSyncedAt());
        assertEquals(status, state.getStatus());
        assertNull(state.getErrorMessage());
    }

    @Test
    void testCatalogSyncStateAllArgsConstructor() {
        Instant now = Instant.now();
        CatalogSyncState state = new CatalogSyncState(2L, 10L, now, "FAILED", "Connection timeout");

        assertNotNull(state);
        assertEquals(2L, state.getId());
        assertEquals(10L, state.getCurrentVersion());
        assertEquals(now, state.getLastSyncedAt());
        assertEquals("FAILED", state.getStatus());
        assertEquals("Connection timeout", state.getErrorMessage());
    }

    @Test
    void testCatalogSyncStateBuilder() {
        Instant now = Instant.now();
        CatalogSyncState state = CatalogSyncState.builder()
                .id(3L)
                .currentVersion(15L)
                .lastSyncedAt(now)
                .status("IN_PROGRESS")
                .errorMessage("None")
                .build();

        assertNotNull(state);
        assertEquals(3L, state.getId());
        assertEquals(15L, state.getCurrentVersion());
        assertEquals(now, state.getLastSyncedAt());
        assertEquals("IN_PROGRESS", state.getStatus());
        assertEquals("None", state.getErrorMessage());
    }
}