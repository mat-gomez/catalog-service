package ar.edu.um.catalog.domain;

import jakarta.persistence.*;
import lombok.*;
import java.time.Instant;

@Entity
@Table(name = "processed_kafka_events")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class ProcessedKafkaEvent {
    @Id
    private String eventId;
    @Column(nullable = false)
    private Instant processedAt;
}
