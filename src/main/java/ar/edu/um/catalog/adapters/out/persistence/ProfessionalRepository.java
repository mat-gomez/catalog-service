package ar.edu.um.catalog.adapters.out.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface ProfessionalRepository extends JpaRepository<ProfessionalEntity, Long> {

    @Query("SELECT p FROM ProfessionalEntity p WHERE (:categoryId IS NULL OR p.categoryId = :categoryId) " +
            "AND (:name IS NULL OR LOWER(p.firstName) LIKE LOWER(CONCAT('%', CAST(:name AS string), '%')) " +
            "OR LOWER(p.lastName) LIKE LOWER(CONCAT('%', CAST(:name AS string), '%'))) " +
            "AND (:enabled IS NULL OR p.enabled = :enabled)")
    List<ProfessionalEntity> findByFilters(@Param("categoryId") Long categoryId,
                                           @Param("name") String name,
                                           @Param("enabled") Boolean enabled);
}