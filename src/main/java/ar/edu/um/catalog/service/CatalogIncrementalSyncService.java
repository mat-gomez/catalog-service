package ar.edu.um.catalog.service;

import ar.edu.um.catalog.adapters.out.persistence.*;
import ar.edu.um.catalog.adapters.out.redis.CatalogRedisAdapter;
import ar.edu.um.catalog.ports.in.CatalogIncrementalSyncUseCase;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
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

    private final ObjectMapper objectMapper;
    private final ProfessionalCategoryRepository categoryRepository;
    private final ProfessionalRepository professionalRepository;
    private final WeeklyScheduleRepository scheduleRepository;

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
            log.info("Aplicando sincronizacion incremental desde {} hasta {}", localVersion + 1, newVersion);

            for (long v = localVersion + 1; v <= newVersion; v++) {
                String changesJson = redisAdapter.getChangesJson(v);

                if (changesJson != null) {
                    log.debug("Procesando delta JSON para version {}: {}", v, changesJson);
                    aplicarCambios(changesJson);
                } else {
                    log.warn("No se encontro el delta para la version {} en Redis", v);
                }
            }

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

    private void aplicarCambios(String changesJson) {
        try {
            JsonNode rootNode = objectMapper.readTree(changesJson);
            JsonNode changesNode = rootNode.path("changes");

            procesarCategorias(changesNode);
            procesarProfesionales(changesNode);
            procesarHorarios(changesNode);

        } catch (Exception e) {
            log.error("Error al deserializar y aplicar los cambios desde Redis", e);
            throw new RuntimeException("Fallo en la mutacion de datos del catalogo", e);
        }
    }

    private void procesarCategorias(JsonNode changesNode) {
        JsonNode categoriesNode = changesNode.path("professionalCategories");
        if (categoriesNode.isArray()) {
            for (JsonNode idNode : categoriesNode) {
                Long id = idNode.asLong();
                String entityJson = redisAdapter.getEntityJson("professional-categories", id);
                if (entityJson != null) {
                    try {
                        ProfessionalCategoryEntity entity = objectMapper.readValue(entityJson, ProfessionalCategoryEntity.class);
                        categoryRepository.save(entity);
                    } catch (Exception e) {
                        log.error("Error al parsear categoria ID {}", id, e);
                        throw new RuntimeException("Error parseando categoria " + id, e);
                    }
                } else {
                    log.warn("No se encontro la categoria con ID {} en Redis", id);
                }
            }
        }
    }

    private void procesarProfesionales(JsonNode changesNode) {
        JsonNode professionalsNode = changesNode.path("professionals");
        if (professionalsNode.isArray()) {
            for (JsonNode idNode : professionalsNode) {
                Long id = idNode.asLong();
                String entityJson = redisAdapter.getEntityJson("professionals", id);
                if (entityJson != null) {
                    try {
                        ProfessionalEntity entity = objectMapper.readValue(entityJson, ProfessionalEntity.class);
                        professionalRepository.save(entity);
                    } catch (Exception e) {
                        log.error("Error al parsear profesional ID {}", id, e);
                        throw new RuntimeException("Error parseando profesional " + id, e);
                    }
                } else {
                    log.warn("No se encontro el profesional con ID {} en Redis", id);
                }
            }
        }
    }

    private void procesarHorarios(JsonNode changesNode) {
        JsonNode schedulesNode = changesNode.path("weeklySchedules");
        if (schedulesNode.isArray()) {
            for (JsonNode idNode : schedulesNode) {
                Long id = idNode.asLong();
                String entityJson = redisAdapter.getEntityJson("weekly-schedules", id);
                if (entityJson != null) {
                    try {
                        WeeklyScheduleEntity entity = objectMapper.readValue(entityJson, WeeklyScheduleEntity.class);
                        scheduleRepository.save(entity);
                    } catch (Exception e) {
                        log.error("Error al parsear horario ID {}", id, e);
                        throw new RuntimeException("Error parseando horario " + id, e);
                    }
                } else {
                    log.warn("No se encontro el horario con ID {} en Redis", id);
                }
            }
        }
    }
}