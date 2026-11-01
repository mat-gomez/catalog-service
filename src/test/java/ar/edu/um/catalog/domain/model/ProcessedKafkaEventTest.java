package ar.edu.um.catalog.domain.model;

import ar.edu.um.catalog.domain.ProcessedKafkaEvent;
import org.junit.jupiter.api.Test;
import java.time.Instant;

import static org.junit.jupiter.api.Assertions.*;

class ProcessedKafkaEventTest {

    @Test
    void testProcessedKafkaEventGettersAndSetters() {
        ProcessedKafkaEvent event = new ProcessedKafkaEvent();

        String eventId = "evt-12345";
        Instant now = Instant.now();

        event.setEventId(eventId);
        event.setProcessedAt(now);

        assertEquals(eventId, event.getEventId());
        assertEquals(now, event.getProcessedAt());
    }

    @Test
    void testProcessedKafkaEventAllArgsConstructor() {
        Instant now = Instant.now();
        ProcessedKafkaEvent event = new ProcessedKafkaEvent("evt-67890", now);

        assertNotNull(event);
        assertEquals("evt-67890", event.getEventId());
        assertEquals(now, event.getProcessedAt());
    }

    @Test
    void testProcessedKafkaEventBuilder() {
        Instant now = Instant.now();
        ProcessedKafkaEvent event = ProcessedKafkaEvent.builder()
                .eventId("evt-abcde")
                .processedAt(now)
                .build();

        assertNotNull(event);
        assertEquals("evt-abcde", event.getEventId());
        assertEquals(now, event.getProcessedAt());
    }
}