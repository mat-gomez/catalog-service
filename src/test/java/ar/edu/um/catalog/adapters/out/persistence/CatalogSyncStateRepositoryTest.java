package ar.edu.um.catalog.adapters.out.persistence;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.Instant;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class CatalogSyncStateRepositoryTest {

    @Autowired
    private CatalogSyncStateRepository repository;

    @Test
    void testSaveAndFindById() {
        Instant now = Instant.now();
        CatalogSyncStateEntity entity = CatalogSyncStateEntity.builder()
                .id(1L)
                .currentVersion(5L)
                .lastSyncedAt(now)
                .status("SUCCESS")
                .build();

        repository.save(entity);

        Optional<CatalogSyncStateEntity> found = repository.findById(1L);

        assertTrue(found.isPresent());
        assertEquals(5L, found.get().getCurrentVersion());
        assertEquals("SUCCESS", found.get().getStatus());
    }
}