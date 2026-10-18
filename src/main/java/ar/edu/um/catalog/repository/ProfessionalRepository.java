package ar.edu.um.catalog.repository;

import ar.edu.um.catalog.domain.Professional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface ProfessionalRepository extends JpaRepository<Professional, Long> {
    @Query("SELECT p FROM Professional p WHERE (:categoryId IS NULL OR p.categoryId = :categoryId) " +
           "AND (:name IS NULL OR LOWER(p.firstName) LIKE LOWER(CONCAT('%', :name, '%')) OR LOWER(p.lastName) LIKE LOWER(CONCAT('%', :name, '%'))) " +
           "AND (:enabled IS NULL OR p.enabled = :enabled)")
    List<Professional> findByFilters(@Param("categoryId") Long categoryId, 
                                     @Param("name") String name, 
                                     @Param("enabled") Boolean enabled);
}
