package ar.edu.um.catalog.domain.model;

import ar.edu.um.catalog.domain.ProcessedKafkaEvent;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class ProcessedKafkaEventTest {

    @Test
    void testProcessedKafkaEventInstantiation() {
        ProcessedKafkaEvent event = new ProcessedKafkaEvent();
        assertNotNull(event);
    }
}
