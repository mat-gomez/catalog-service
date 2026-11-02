package ar.edu.um.catalog.adapters.out.persistence;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.Instant;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ProcessedKafkaEventRepositoryTest {

    @Autowired
    private ProcessedKafkaEventRepository repository;

    @Test
    void testExistsByEventId() {
        String eventId = "evt-test-123";
        ProcessedKafkaEventEntity entity = ProcessedKafkaEventEntity.builder()
                .eventId(eventId)
                .processedAt(Instant.now())
                .build();

        repository.save(entity);

        boolean exists = repository.existsByEventId(eventId);
        boolean notExists = repository.existsByEventId("evt-non-existent");

        assertTrue(exists);
        assertFalse(notExists);
    }
}