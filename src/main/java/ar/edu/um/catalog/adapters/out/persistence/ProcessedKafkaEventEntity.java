package ar.edu.um.catalog.adapters.out.persistence;

import jakarta.persistence.*;
import lombok.*;
import java.time.Instant;

@Entity
@Table(name = "processed_kafka_events")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class ProcessedKafkaEventEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String eventId;

    private Instant processedAt;
}