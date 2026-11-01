package ar.edu.um.catalog.adapters.out.persistence;

import jakarta.persistence.*;
import lombok.*;
import java.time.Instant;
import java.time.LocalTime;

@Entity
@Table(name = "weekly_schedules")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class WeeklyScheduleEntity {
    @Id
    private Long id;
    @Column(nullable = false)
    private Long professionalId;
    @Column(nullable = false)
    private String dayOfWeek;
    @Column(nullable = false)
    private LocalTime startTime;
    @Column(nullable = false)
    private LocalTime endTime;
    @Column(nullable = false)
    private Integer slotDurationMinutes;
    @Column(nullable = false)
    private Boolean enabled;
    private Instant createdAt;
    private Instant updatedAt;
}