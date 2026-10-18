package ar.edu.um.catalog.repository;

import ar.edu.um.catalog.domain.WeeklySchedule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface WeeklyScheduleRepository extends JpaRepository<WeeklySchedule, Long> {
    List<WeeklySchedule> findByProfessionalId(Long professionalId);
}
