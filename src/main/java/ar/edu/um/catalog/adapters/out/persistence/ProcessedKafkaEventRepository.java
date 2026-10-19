package ar.edu.um.catalog.adapters.out.persistence;

import ar.edu.um.catalog.domain.ProcessedKafkaEvent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProcessedKafkaEventRepository extends JpaRepository<ProcessedKafkaEvent, String> {
}
