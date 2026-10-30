package ar.edu.um.catalog.service;

import ar.edu.um.catalog.adapters.out.persistence.*;
import ar.edu.um.catalog.ports.in.CatalogSyncUseCase;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestClient;

import java.time.Instant;
import java.time.LocalTime;
import java.util.List;
import java.util.Map;

@Slf4j
@Service
@RequiredArgsConstructor
public class CatalogSyncService implements CatalogSyncUseCase {

    private final RestClient catedraRestClient;
    private final ProfessionalCategoryRepository categoryRepository;
    private final ProfessionalRepository professionalRepository;
    private final WeeklyScheduleRepository scheduleRepository;
    private final CatalogSyncStateRepository syncStateRepository;

    @Override
    @Transactional
    public void executeFullSnapshotSync() {
        log.info("Iniciando sincronizacion completa por Snapshot...");

        Map<String, Object> response = catedraRestClient.get()
                .uri("/api/synchronization/snapshot")
                .retrieve()
                .body(new ParameterizedTypeReference<>() {});

        if (response == null) {
            throw new RuntimeException("No se pudo obtener el snapshot del catalogo desde la catedra.");
        }

        Integer snapshotVersionInt = (Integer) response.get("snapshotVersion");
        Long snapshotVersion = snapshotVersionInt.longValue();

        Object catObj = response.get("professionalCategories");
        if (catObj instanceof List<?>) {
            List<?> catList = (List<?>) catObj;
            for (Object item : catList) {
                if (item instanceof Map<?, ?> c) {
                    ProfessionalCategoryEntity catEntity = new ProfessionalCategoryEntity();
                    catEntity.setId(((Number) c.get("id")).longValue());
                    catEntity.setName((String) c.get("name"));
                    catEntity.setDescription((String) c.get("description"));
                    catEntity.setEnabled((Boolean) c.get("enabled"));
                    catEntity.setCreatedAt(parseInstant(c.get("createdAt")));
                    catEntity.setUpdatedAt(parseInstant(c.get("updatedAt")));
                    categoryRepository.save(catEntity);
                }
            }
        }

        Object profObj = response.get("professionals");
        if (profObj instanceof List<?>) {
            List<?> profList = (List<?>) profObj;
            for (Object item : profList) {
                if (item instanceof Map<?, ?> p) {
                    ProfessionalEntity profEntity = new ProfessionalEntity();
                    profEntity.setId(((Number) p.get("id")).longValue());
                    profEntity.setCategoryId(((Number) p.get("categoryId")).longValue());
                    profEntity.setFirstName((String) p.get("firstName"));
                    profEntity.setLastName((String) p.get("lastName"));
                    profEntity.setEnabled((Boolean) p.get("enabled"));
                    profEntity.setCreatedAt(parseInstant(p.get("createdAt")));
                    profEntity.setUpdatedAt(parseInstant(p.get("updatedAt")));
                    professionalRepository.save(profEntity);
                }
            }
        }

        Object schedObj = response.get("weeklySchedules");
        if (schedObj instanceof List<?>) {
            List<?> schedList = (List<?>) schedObj;
            for (Object item : schedList) {
                if (item instanceof Map<?, ?> s) {
                    WeeklyScheduleEntity schedEntity = new WeeklyScheduleEntity();
                    schedEntity.setId(((Number) s.get("id")).longValue());
                    schedEntity.setProfessionalId(((Number) s.get("professionalId")).longValue());
                    schedEntity.setDayOfWeek((String) s.get("dayOfWeek"));
                    schedEntity.setStartTime(LocalTime.parse((String) s.get("startTime")));
                    schedEntity.setEndTime(LocalTime.parse((String) s.get("endTime")));
                    schedEntity.setSlotDurationMinutes(((Number) s.get("slotDurationMinutes")).intValue());
                    schedEntity.setEnabled((Boolean) s.get("enabled"));
                    schedEntity.setCreatedAt(parseInstant(s.get("createdAt")));
                    schedEntity.setUpdatedAt(parseInstant(s.get("updatedAt")));
                    scheduleRepository.save(schedEntity);
                }
            }
        }

        CatalogSyncStateEntity state = syncStateRepository.findById(1L).orElse(new CatalogSyncStateEntity());
        state.setId(1L);
        state.setCurrentVersion(snapshotVersion);
        state.setLastSyncedAt(Instant.now());
        state.setStatus("SUCCESS");

        syncStateRepository.save(state);

        log.info("Sincronizacion completa finalizada con exito. Version aplicada: {}", snapshotVersion);
    }

    private Instant parseInstant(Object val) {
        if (val == null) return null;
        return Instant.parse(val.toString());
    }
}