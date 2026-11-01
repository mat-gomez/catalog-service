package ar.edu.um.catalog.adapters.in.messaging;

import ar.edu.um.catalog.ports.in.CatalogIncrementalSyncUseCase;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CatalogKafkaConsumerTest {

    @Mock
    private CatalogIncrementalSyncUseCase incrementalSyncUseCase;

    @Spy
    private ObjectMapper objectMapper = new ObjectMapper();

    @InjectMocks
    private CatalogKafkaConsumer catalogKafkaConsumer;

    @Test
    void testHandleCatalogUpdated_Success() {
        String message = "{\"eventType\":\"CatalogUpdated\",\"eventId\":\"evt-123\",\"newVersion\":10}";

        catalogKafkaConsumer.handleCatalogUpdated(message);

        verify(incrementalSyncUseCase, times(1)).processCatalogUpdate("evt-123", 10L);
    }

    @Test
    void testHandleCatalogUpdated_DifferentEventType() {
        String message = "{\"eventType\":\"OtherEvent\",\"eventId\":\"evt-123\",\"newVersion\":10}";

        catalogKafkaConsumer.handleCatalogUpdated(message);

        verify(incrementalSyncUseCase, never()).processCatalogUpdate(anyString(), anyLong());
    }

    @Test
    void testHandleCatalogUpdated_ExceptionHandling() {
        String malformedMessage = "{invalid-json}";

        catalogKafkaConsumer.handleCatalogUpdated(malformedMessage);

        verify(incrementalSyncUseCase, never()).processCatalogUpdate(anyString(), anyLong());
    }
}