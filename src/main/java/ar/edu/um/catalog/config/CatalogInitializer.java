package ar.edu.um.catalog.config;

import ar.edu.um.catalog.adapters.out.persistence.ProfessionalRepository;
import ar.edu.um.catalog.ports.in.CatalogSyncUseCase;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class CatalogInitializer {

    private final ProfessionalRepository professionalRepository;
    private final CatalogSyncUseCase catalogSyncUseCase;

    @EventListener(ApplicationReadyEvent.class)
    public void onApplicationReady() {
        try {
            if (professionalRepository.count() == 0) {
                log.info("Base de datos local de catálogo vacía detectada al iniciar. Ejecutando snapshot inicial...");
                catalogSyncUseCase.executeFullSnapshotSync();
                log.info("Snapshot inicial ejecutado correctamente.");
            } else {
                log.info("Base de datos local inicializada con datos existentes.");
            }
        } catch (Exception e) {
            log.error("Error al intentar sincronizar el snapshot inicial al arrancar la aplicación: {}", e.getMessage(), e);
        }
    }
}