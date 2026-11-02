package ar.edu.um.catalog.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.Instant;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SnapshotCatalogo {
    private Long version;
    private Instant timestamp;
    private List<Professional> professionals;
    private List<ProfessionalCategory> categories;
    private List<WeeklySchedule> schedules;
}