package ar.edu.um.catalog.domain;

import lombok.*;
import java.time.Instant;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class ProcessedKafkaEvent {
    private String eventId;
    private Instant processedAt;
}