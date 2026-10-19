package ar.edu.um.catalog.ports.in;

public interface CatalogIncrementalSyncUseCase {
    void processCatalogUpdate(String eventId, Long newVersion);
}
