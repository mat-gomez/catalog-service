package ar.edu.um.catalog.adapters.in.messaging;

import ar.edu.um.catalog.adapters.in.messaging.CatalogKafkaConsumer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class CatalogKafkaConsumerTest {

    @InjectMocks
    private CatalogKafkaConsumer catalogKafkaConsumer;

    @Test
    void testConsumerInstantiation() {
        assertNotNull(catalogKafkaConsumer);
    }
}
