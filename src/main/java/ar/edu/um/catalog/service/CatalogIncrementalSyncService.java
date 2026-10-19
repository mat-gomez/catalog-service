package ar.edu.um.catalog.service;

import ar.edu.um.catalog.adapters.out.persistence.CatalogSyncStateRepository;
import ar.edu.um.catalog.adapters.out.persistence.ProcessedKafkaEventRepository;
import ar.edu.um.catalog.adapters.out.redis.CatalogRedisAdapter;
import ar.edu.um.catalog.domain.CatalogSyncState;
import ar.edu.um.catalog.domain.ProcessedKafkaEvent;
import ar.edu.um.catalog.ports.in.CatalogIncrementalSyncUseCase;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;

@Slf4j
@Service
@RequiredArgsConstructor
public class CatalogIncrementalSyncService implements CatalogIncrementalSyncUseCase {

    private final CatalogRedisAdapter redisAdapter;
    private final ProcessedKafkaEventRepository processedEventRepository;
    private final CatalogSyncStateRepository syncStateRepository;
    private final CatalogSyncService fullSyncService;

    @Override
    @Transactional
    public void processCatalogUpdate(String eventId, Long newVersion) {
        // 1. Validar idempotencia: si el evento ya fue procesado, se ignora
        if (processedEventRepository.existsById(eventId)) {
            log.info("Evento Kafka duplicado detectado e ignorado. EventId: {}", eventId);
            return;
        }

        // 2. Obtener la version local actual aplicada
        CatalogSyncState currentState = syncStateRepository.findById(1L).orElse(null);
        Long localVersion = (currentState != null) ? currentState.getCurrentVersion() : 0L;

        // 3. Consultar la version mas vieja disponible en Redis para detectar discontinuidades
        Long oldestAvailable = redisAdapter.getOldestAvailableVersion();

        log.info("Procesando actualizacion de catalogo. Version local: {}, Nueva version: {}, Mas vieja en Redis: {}", 
                localVersion, newVersion, oldestAvailable);

        // Si la version local esta por debajo de la ventana disponible en Redis, hacemos un Snapshot completo
        if (oldestAvailable != null && localVersion < oldestAvailable) {
            log.warn("Discontinuidad detectada. La version local ({}) es anterior a la minima en Redis ({}). Ejecutando Snapshot completo...", 
                    localVersion, oldestAvailable);
            fullSyncService.executeFullSnapshotSync();
        } else if (newVersion > localVersion) {
            // Logica incremental basica: si hay nueva version, avanzamos sincronizando por ahora
            log.info("Aplicando sincronizacion incremental hasta la version {}", newVersion);
            // Aqui aplicaremos los cambios incrementales leyendo Redis paso a paso
            
            // Actualizar version local de forma consistente
            if (currentState == null) {
                currentState = new CatalogSyncState();
                currentState.setId(1L);
            }
            currentState.setCurrentVersion(newVersion);
            currentState.setLastSyncedAt(Instant.now());
            currentState.setStatus("SUCCESS");
            syncStateRepository.save(currentState);
        }

        // 4. Registrar el evento como procesado para garantizar la idempotencia
        ProcessedKafkaEvent processedEvent = ProcessedKafkaEvent.builder()
                .eventId(eventId)
                .processedAt(Instant.now())
                .build();
        processedEventRepository.save(processedEvent);

        log.info("Procesamiento del evento de catalogo finalizado con exito.");
    }
}
