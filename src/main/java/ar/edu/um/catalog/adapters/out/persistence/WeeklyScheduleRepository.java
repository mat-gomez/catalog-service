package ar.edu.um.catalog.adapters.out.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface WeeklyScheduleRepository extends JpaRepository<WeeklyScheduleEntity, Long> {
    List<WeeklyScheduleEntity> findByProfessionalId(Long professionalId);
}