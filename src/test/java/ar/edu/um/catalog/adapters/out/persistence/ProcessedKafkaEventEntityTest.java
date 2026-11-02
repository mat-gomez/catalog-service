package ar.edu.um.catalog.adapters.out.persistence;

import org.junit.jupiter.api.Test;
import java.time.Instant;

import static org.junit.jupiter.api.Assertions.*;

class ProcessedKafkaEventEntityTest {

    @Test
    void testGettersAndSetters() {
        ProcessedKafkaEventEntity entity = new ProcessedKafkaEventEntity();
        Long id = 1L;
        String eventId = "evt-001";
        Instant now = Instant.now();

        entity.setId(id);
        entity.setEventId(eventId);
        entity.setProcessedAt(now);

        assertEquals(id, entity.getId());
        assertEquals(eventId, entity.getEventId());
        assertEquals(now, entity.getProcessedAt());
    }

    @Test
    void testAllArgsConstructor() {
        Instant now = Instant.now();
        ProcessedKafkaEventEntity entity = new ProcessedKafkaEventEntity(1L, "evt-002", now);

        assertNotNull(entity);
        assertEquals(1L, entity.getId());
        assertEquals("evt-002", entity.getEventId());
        assertEquals(now, entity.getProcessedAt());
    }

    @Test
    void testBuilder() {
        Instant now = Instant.now();
        ProcessedKafkaEventEntity entity = ProcessedKafkaEventEntity.builder()
                .id(2L)
                .eventId("evt-003")
                .processedAt(now)
                .build();

        assertNotNull(entity);
        assertEquals(2L, entity.getId());
        assertEquals("evt-003", entity.getEventId());
        assertEquals(now, entity.getProcessedAt());
    }
}