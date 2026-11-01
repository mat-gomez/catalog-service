package ar.edu.um.catalog.service;

import ar.edu.um.catalog.adapters.out.persistence.CatalogSyncStateEntity;
import ar.edu.um.catalog.adapters.out.persistence.CatalogSyncStateRepository;
import ar.edu.um.catalog.adapters.out.persistence.ProcessedKafkaEventEntity;
import ar.edu.um.catalog.adapters.out.persistence.ProcessedKafkaEventRepository;
import ar.edu.um.catalog.adapters.out.redis.CatalogRedisAdapter;
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
        if (processedEventRepository.existsByEventId(eventId)) {
            log.info("Evento Kafka duplicado detectado e ignorado. EventId: {}", eventId);
            return;
        }

        CatalogSyncStateEntity currentState = syncStateRepository.findById(1L).orElse(null);
        Long localVersion = (currentState != null) ? currentState.getCurrentVersion() : 0L;

        Long oldestAvailable = redisAdapter.getOldestAvailableVersion();

        log.info("Procesando actualizacion de catalogo. Version local: {}, Nueva version: {}, Mas vieja en Redis: {}",
                localVersion, newVersion, oldestAvailable);

        if (oldestAvailable != null && localVersion < oldestAvailable) {
            log.warn("Discontinuidad detectada. La version local ({}) es anterior a la minima en Redis ({}). Ejecutando Snapshot completo...",
                    localVersion, oldestAvailable);
            fullSyncService.executeFullSnapshotSync();
        } else if (newVersion > localVersion) {
            log.info("Aplicando sincronizacion incremental hasta la version {}", newVersion);

            if (currentState == null) {
                currentState = new CatalogSyncStateEntity();
                currentState.setId(1L);
            }
            currentState.setCurrentVersion(newVersion);
            currentState.setLastSyncedAt(Instant.now());
            currentState.setStatus("SUCCESS");
            syncStateRepository.save(currentState);
        }

        ProcessedKafkaEventEntity processedEvent = ProcessedKafkaEventEntity.builder()
                .eventId(eventId)
                .processedAt(Instant.now())
                .build();
        processedEventRepository.save(processedEvent);

        log.info("Procesamiento del evento de catalogo finalizado con exito.");
    }
}