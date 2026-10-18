package ar.edu.um.catalog.service;

import ar.edu.um.catalog.domain.*;
import ar.edu.um.catalog.repository.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestClient;

import java.time.Instant;
import java.util.List;
import java.util.Map;

@Slf4j
@Service
@RequiredArgsConstructor
public class CatalogSyncService {

    private final RestClient catedraRestClient;
    private final ProfessionalCategoryRepository categoryRepository;
    private final ProfessionalRepository professionalRepository;
    private final WeeklyScheduleRepository scheduleRepository;
    private final CatalogSyncStateRepository syncStateRepository;

    @Transactional
    public void executeFullSnapshotSync() {
        log.info("Iniciando sincronizacion completa por Snapshot...");

        // Consumir el endpoint REST de snapshot de la catedra
        Map<String, Object> response = catedraRestClient.get()
                .uri("/api/synchronization/snapshot")
                .retrieve()
                .body(new ParameterizedTypeReference<>() {});

        if (response == null) {
            throw new RuntimeException("No se pudo obtener el snapshot del catalogo desde la catedra.");
        }

        Integer snapshotVersionInt = (Integer) response.get("snapshotVersion");
        Long snapshotVersion = snapshotVersionInt.longValue();

        // Mapear y guardar categorias
        List<Map<String, Object>> catList = (List<Map<String, Object>>) response.get("professionalCategories");
        if (catList != null) {
            for (Map<String, Object> c : catList) {
                ProfessionalCategory cat = ProfessionalCategory.builder()
                        .id(((Number) c.get("id")).longValue())
                        .name((String) c.get("name"))
                        .description((String) c.get("description"))
                        .enabled((Boolean) c.get("enabled"))
                        .createdAt(parseInstant(c.get("createdAt")))
                        .updatedAt(parseInstant(c.get("updatedAt")))
                        .build();
                categoryRepository.save(cat);
            }
        }

        // Mapear y guardar profesionales
        List<Map<String, Object>> profList = (List<Map<String, Object>>) response.get("professionals");
        if (profList != null) {
            for (Map<String, Object> p : profList) {
                Professional prof = Professional.builder()
                        .id(((Number) p.get("id")).longValue())
                        .categoryId(((Number) p.get("categoryId")).longValue())
                        .firstName((String) p.get("firstName"))
                        .lastName((String) p.get("lastName"))
                        .enabled((Boolean) p.get("enabled"))
                        .createdAt(parseInstant(p.get("createdAt")))
                        .updatedAt(parseInstant(p.get("updatedAt")))
                        .build();
                professionalRepository.save(prof);
            }
        }

        // Mapear y guardar horarios semanales
        List<Map<String, Object>> schedList = (List<Map<String, Object>>) response.get("weeklySchedules");
        if (schedList != null) {
            for (Map<String, Object> s : schedList) {
                WeeklySchedule sched = WeeklySchedule.builder()
                        .id(((Number) s.get("id")).longValue())
                        .professionalId(((Number) s.get("professionalId")).longValue())
                        .dayOfWeek((String) s.get("dayOfWeek"))
                        .startTime(java.time.LocalTime.parse((String) s.get("startTime")))
                        .endTime(java.time.LocalTime.parse((String) s.get("endTime")))
                        .slotDurationMinutes(((Number) s.get("slotDurationMinutes")).intValue())
                        .enabled((Boolean) s.get("enabled"))
                        .createdAt(parseInstant(s.get("createdAt")))
                        .updatedAt(parseInstant(s.get("updatedAt")))
                        .build();
                scheduleRepository.save(sched);
            }
        }

        // Registrar la version local aplicada de forma consistente
        CatalogSyncState state = syncStateRepository.findById(1L).orElse(new CatalogSyncState());
        state.setId(1L);
        state.setCurrentVersion(snapshotVersion);
        state.setLastSyncedAt(Instant.now());
        state.setStatus("SUCCESS");
        state.setErrorMessage(null);
        syncStateRepository.save(state);

        log.info("Sincronizacion completa finalizada con exito. Version aplicada: {}", snapshotVersion);
    }

    private Instant parseInstant(Object val) {
        if (val == null) return null;
        return Instant.parse(val.toString());
    }
}
