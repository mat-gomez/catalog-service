package ar.edu.um.catalog.domain.model;

import ar.edu.um.catalog.domain.SnapshotCatalogo;
import org.junit.jupiter.api.Test;
import java.time.Instant;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;

class SnapshotCatalogoTest {

    @Test
    void testSnapshotBuilderAndGetters() {
        Instant now = Instant.now();
        SnapshotCatalogo snapshot = SnapshotCatalogo.builder()
                .version(1L)
                .timestamp(now)
                .professionals(Collections.emptyList())
                .categories(Collections.emptyList())
                .schedules(Collections.emptyList())
                .build();

        assertNotNull(snapshot);
        assertEquals(1L, snapshot.getVersion());
        assertEquals(now, snapshot.getTimestamp());
        assertTrue(snapshot.getProfessionals().isEmpty());
    }
}