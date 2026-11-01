package ar.edu.um.catalog.adapters.out.persistence;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.Instant;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class PersistenceTest {

    @Autowired
    private ProcessedKafkaEventRepository processedEventRepository;

    @Autowired
    private CatalogSyncStateRepository syncStateRepository;

    @Test
    void testProcessedKafkaEventRepository() {
        ProcessedKafkaEventEntity event = ProcessedKafkaEventEntity.builder()
                .eventId("evt-test-123")
                .processedAt(Instant.now())
                .build();

        processedEventRepository.save(event);

        boolean exists = processedEventRepository.existsByEventId("evt-test-123");
        assertThat(exists).isTrue();
    }

    @Test
    void testCatalogSyncStateRepository() {
        CatalogSyncStateEntity state = CatalogSyncStateEntity.builder()
                .id(999L)
                .currentVersion(50L)
                .lastSyncedAt(Instant.now())
                .status("SUCCESS")
                .build();

        syncStateRepository.save(state);

        CatalogSyncStateEntity found = syncStateRepository.findById(999L).orElse(null);
        assertThat(found).isNotNull();
        assertThat(found.getCurrentVersion()).isEqualTo(50L);
    }
}