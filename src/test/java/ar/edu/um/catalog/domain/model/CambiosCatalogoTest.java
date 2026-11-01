package ar.edu.um.catalog.domain.model;

import ar.edu.um.catalog.domain.CambiosCatalogo;
import org.junit.jupiter.api.Test;
import java.time.Instant;

import static org.junit.jupiter.api.Assertions.*;

class CambiosCatalogoTest {

    @Test
    void testCambiosCatalogoModel() {
        Instant now = Instant.now();
        CambiosCatalogo cambios = CambiosCatalogo.builder()
                .targetVersion(5L)
                .lastUpdated(now)
                .hasChanges(true)
                .build();

        assertNotNull(cambios);
        assertEquals(5L, cambios.getTargetVersion());
        assertEquals(now, cambios.getLastUpdated());
        assertTrue(cambios.getHasChanges());
    }
}