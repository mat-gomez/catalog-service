package ar.edu.um.catalog.domain.model;

import ar.edu.um.catalog.domain.CatalogSyncState;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class CatalogSyncStateTest {

    @Test
    void testCatalogSyncStateInstantiation() {
        CatalogSyncState state = new CatalogSyncState();
        assertNotNull(state);
    }
}
