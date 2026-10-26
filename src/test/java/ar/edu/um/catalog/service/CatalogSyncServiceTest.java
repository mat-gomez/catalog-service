package ar.edu.um.catalog.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class CatalogSyncServiceTest {

    @InjectMocks
    private CatalogSyncService catalogSyncService;

    @Test
    void testServiceInstantiation() {
        assertNotNull(catalogSyncService);
    }
}
