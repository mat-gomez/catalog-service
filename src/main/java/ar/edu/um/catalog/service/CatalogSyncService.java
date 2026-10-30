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

    private static final String FIELD_ID = "id";
    private static final String FIELD_NAME = "name";
    private static final String FIELD_DESCRIPTION = "description";
    private static final String FIELD_ENABLED = "enabled";
    private static final String FIELD_CREATED_AT = "createdAt";
    private static final String FIELD_UPDATED_AT = "updatedAt";
    private static final String FIELD_CATEGORY_ID = "categoryId";
    private static final String FIELD_FIRST_NAME = "firstName";
    private static final String FIELD_LAST_NAME = "lastName";
    private static final String FIELD_PROFESSIONAL_ID = "professionalId";
    private static final String FIELD_DAY_OF_WEEK = "dayOfWeek";
    private static final String FIELD_START_TIME = "startTime";
    private static final String FIELD_END_TIME = "endTime";
    private static final String FIELD_SLOT_DURATION_MINUTES = "slotDurationMinutes";

    private static final String SNAPSHOT_URI = "/api/synchronization/snapshot";
    private static final String ERROR_SNAPSHOT_MSG = "No se pudo obtener el snapshot del catalogo desde la catedra.";

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
                .uri(SNAPSHOT_URI)
                .retrieve()
                .body(new ParameterizedTypeReference<>() {});

        if (response == null) {
            throw new IllegalStateException(ERROR_SNAPSHOT_MSG);
        }

        Integer snapshotVersionInt = (Integer) response.get("snapshotVersion");
        Long snapshotVersion = snapshotVersionInt != null ? snapshotVersionInt.longValue() : 0L;

        processCategories(response.get("professionalCategories"));
        processProfessionals(response.get("professionals"));
        processSchedules(response.get("weeklySchedules"));

        updateSyncState(snapshotVersion);

        log.info("Sincronizacion completa finalizada con exito. Version aplicada: {}", snapshotVersion);
    }

    private void processCategories(Object catObj) {
        if (catObj instanceof List<?> catList) {
            for (Object item : catList) {
                if (item instanceof Map<?, ?> c) {
                    ProfessionalCategoryEntity entity = new ProfessionalCategoryEntity();
                    entity.setId(((Number) c.get(FIELD_ID)).longValue());
                    entity.setName((String) c.get(FIELD_NAME));
                    entity.setDescription((String) c.get(FIELD_DESCRIPTION));
                    entity.setEnabled((Boolean) c.get(FIELD_ENABLED));
                    entity.setCreatedAt(parseInstant(c.get(FIELD_CREATED_AT)));
                    entity.setUpdatedAt(parseInstant(c.get(FIELD_UPDATED_AT)));
                    categoryRepository.save(entity);
                }
            }
        }
    }

    private void processProfessionals(Object profObj) {
        if (profObj instanceof List<?> profList) {
            for (Object item : profList) {
                if (item instanceof Map<?, ?> p) {
                    ProfessionalEntity entity = new ProfessionalEntity();
                    entity.setId(((Number) p.get(FIELD_ID)).longValue());
                    entity.setCategoryId(((Number) p.get(FIELD_CATEGORY_ID)).longValue());
                    entity.setFirstName((String) p.get(FIELD_FIRST_NAME));
                    entity.setLastName((String) p.get(FIELD_LAST_NAME));
                    entity.setEnabled((Boolean) p.get(FIELD_ENABLED));
                    entity.setCreatedAt(parseInstant(p.get(FIELD_CREATED_AT)));
                    entity.setUpdatedAt(parseInstant(p.get(FIELD_UPDATED_AT)));
                    professionalRepository.save(entity);
                }
            }
        }
    }

    private void processSchedules(Object schedObj) {
        if (schedObj instanceof List<?> schedList) {
            for (Object item : schedList) {
                if (item instanceof Map<?, ?> s) {
                    WeeklyScheduleEntity entity = new WeeklyScheduleEntity();
                    entity.setId(((Number) s.get(FIELD_ID)).longValue());
                    entity.setProfessionalId(((Number) s.get(FIELD_PROFESSIONAL_ID)).longValue());
                    entity.setDayOfWeek((String) s.get(FIELD_DAY_OF_WEEK));
                    entity.setStartTime(LocalTime.parse((String) s.get(FIELD_START_TIME)));
                    entity.setEndTime(LocalTime.parse((String) s.get(FIELD_END_TIME)));
                    entity.setSlotDurationMinutes(((Number) s.get(FIELD_SLOT_DURATION_MINUTES)).intValue());
                    entity.setEnabled((Boolean) s.get(FIELD_ENABLED));
                    entity.setCreatedAt(parseInstant(s.get(FIELD_CREATED_AT)));
                    entity.setUpdatedAt(parseInstant(s.get(FIELD_UPDATED_AT)));
                    scheduleRepository.save(entity);
                }
            }
        }
    }

    private void updateSyncState(Long snapshotVersion) {
        CatalogSyncStateEntity state = syncStateRepository.findById(1L).orElseGet(CatalogSyncStateEntity::new);
        state.setId(1L);
        state.setCurrentVersion(snapshotVersion);
        state.setLastSyncedAt(Instant.now());
        state.setStatus("SUCCESS");
        syncStateRepository.save(state);
    }

    private Instant parseInstant(Object val) {
        if (val == null) return null;
        return Instant.parse(val.toString());
    }
}