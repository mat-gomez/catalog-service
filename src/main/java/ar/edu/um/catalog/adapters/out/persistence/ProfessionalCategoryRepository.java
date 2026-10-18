package ar.edu.um.catalog.adapters.out.persistence;

import ar.edu.um.catalog.domain.ProfessionalCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProfessionalCategoryRepository extends JpaRepository<ProfessionalCategory, Long> {
}
