package ar.edu.um.catalog.domain;

import lombok.*;
import java.time.Instant;
import java.time.LocalTime;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class WeeklySchedule {
    private Long id;
    private Long professionalId;
    private String dayOfWeek;
    private LocalTime startTime;
    private LocalTime endTime;
    private Integer slotDurationMinutes;
    private Boolean enabled;
    private Instant createdAt;
    private Instant updatedAt;
}