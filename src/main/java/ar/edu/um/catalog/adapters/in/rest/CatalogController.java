package ar.edu.um.catalog.adapters.in.rest;

import ar.edu.um.catalog.domain.Professional;
import ar.edu.um.catalog.domain.ProfessionalCategory;
import ar.edu.um.catalog.domain.WeeklySchedule;
import ar.edu.um.catalog.adapters.out.persistence.ProfessionalCategoryEntity;
import ar.edu.um.catalog.adapters.out.persistence.ProfessionalCategoryRepository;
import ar.edu.um.catalog.adapters.out.persistence.ProfessionalEntity;
import ar.edu.um.catalog.adapters.out.persistence.ProfessionalRepository;
import ar.edu.um.catalog.adapters.out.persistence.WeeklyScheduleEntity;
import ar.edu.um.catalog.adapters.out.persistence.WeeklyScheduleRepository;
import ar.edu.um.catalog.ports.in.CatalogSyncUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/catalog")
@RequiredArgsConstructor
public class CatalogController {

    private final ProfessionalCategoryRepository categoryRepository;
    private final ProfessionalRepository professionalRepository;
    private final WeeklyScheduleRepository scheduleRepository;
    private final CatalogSyncUseCase catalogSyncUseCase;

    @PostMapping("/sync")
    public ResponseEntity<String> triggerSync() {
        catalogSyncUseCase.executeFullSnapshotSync();
        return ResponseEntity.ok("Sincronizacion completa ejecutada correctamente.");
    }

    @GetMapping("/categories")
    public ResponseEntity<List<ProfessionalCategory>> getAllCategories() {
        List<ProfessionalCategoryEntity> entities = categoryRepository.findAll();
        List<ProfessionalCategory> categories = entities.stream()
                .map(e -> ProfessionalCategory.builder()
                        .id(e.getId())
                        .name(e.getName())
                        .description(e.getDescription())
                        .enabled(e.getEnabled())
                        .createdAt(e.getCreatedAt())
                        .updatedAt(e.getUpdatedAt())
                        .build())
                .toList();
        return ResponseEntity.ok(categories);
    }

    @GetMapping("/professionals")
    public ResponseEntity<List<Professional>> searchProfessionals(
            @RequestParam(required = false) Long categoryId,
            @RequestParam(required = false) String name,
            @RequestParam(required = false) Boolean enabled) {

        List<ProfessionalEntity> entities = professionalRepository.findByFilters(categoryId, name, enabled);

        List<Professional> professionals = entities.stream()
                .map(e -> Professional.builder()
                        .id(e.getId())
                        .categoryId(e.getCategoryId())
                        .firstName(e.getFirstName())
                        .lastName(e.getLastName())
                        .enabled(e.getEnabled())
                        .createdAt(e.getCreatedAt())
                        .updatedAt(e.getUpdatedAt())
                        .build())
                .toList();

        return ResponseEntity.ok(professionals);
    }

    @GetMapping("/professionals/{professionalId}/schedules")
    public ResponseEntity<List<WeeklySchedule>> getProfessionalSchedules(@PathVariable Long professionalId) {
        List<WeeklyScheduleEntity> entities = scheduleRepository.findByProfessionalId(professionalId);
        List<WeeklySchedule> schedules = entities.stream()
                .map(e -> WeeklySchedule.builder()
                        .id(e.getId())
                        .professionalId(e.getProfessionalId())
                        .dayOfWeek(e.getDayOfWeek())
                        .startTime(e.getStartTime())
                        .endTime(e.getEndTime())
                        .slotDurationMinutes(e.getSlotDurationMinutes())
                        .enabled(e.getEnabled())
                        .createdAt(e.getCreatedAt())
                        .updatedAt(e.getUpdatedAt())
                        .build())
                .toList();
        return ResponseEntity.ok(schedules);
    }
}