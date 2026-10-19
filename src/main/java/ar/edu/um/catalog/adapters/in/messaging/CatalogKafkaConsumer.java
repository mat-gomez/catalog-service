package ar.edu.um.catalog.adapters.in.messaging;

import ar.edu.um.catalog.ports.in.CatalogIncrementalSyncUseCase;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class CatalogKafkaConsumer {

    private final CatalogIncrementalSyncUseCase incrementalSyncUseCase;
    private final ObjectMapper objectMapper;

    @KafkaListener(topics = "${catedra.kafka.catalog-topic}", groupId = "${spring.kafka.consumer.group-id}")
    public void handleCatalogUpdated(@Payload String message) {
        try {
            JsonNode jsonNode = objectMapper.readTree(message);
            String eventType = jsonNode.get("eventType").asText();

            if ("CatalogUpdated".equals(eventType)) {
                String eventId = jsonNode.get("eventId").asText();
                Long newVersion = jsonNode.get("newVersion").asLong();

                log.info("Evento CatalogUpdated recibido por Kafka. EventId: {}, NewVersion: {}", eventId, newVersion);
                incrementalSyncUseCase.processCatalogUpdate(eventId, newVersion);
            }
        } catch (Exception e) {
            log.error("Error al procesar el mensaje de Kafka del catálogo", e);
        }
    }
}
