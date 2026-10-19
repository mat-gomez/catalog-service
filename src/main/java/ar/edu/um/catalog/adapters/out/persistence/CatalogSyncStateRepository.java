package ar.edu.um.catalog.adapters.out.persistence;

import ar.edu.um.catalog.domain.CatalogSyncState;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CatalogSyncStateRepository extends JpaRepository<CatalogSyncState, Long> {
}
