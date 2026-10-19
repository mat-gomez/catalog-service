package ar.edu.um.catalog.adapters.in.rest;

import ar.edu.um.catalog.domain.Professional;
import ar.edu.um.catalog.domain.ProfessionalCategory;
import ar.edu.um.catalog.domain.WeeklySchedule;
import ar.edu.um.catalog.adapters.out.persistence.ProfessionalCategoryRepository;
import ar.edu.um.catalog.adapters.out.persistence.ProfessionalRepository;
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
        return ResponseEntity.ok(categoryRepository.findAll());
    }

    @GetMapping("/professionals")
    public ResponseEntity<List<Professional>> searchProfessionals(
            @RequestParam(required = false) Long categoryId,
            @RequestParam(required = false) String name,
            @RequestParam(required = false) Boolean enabled) {
        List<Professional> professionals = professionalRepository.findByFilters(categoryId, name, enabled);
        return ResponseEntity.ok(professionals);
    }

    @GetMapping("/professionals/{professionalId}/schedules")
    public ResponseEntity<List<WeeklySchedule>> getProfessionalSchedules(@PathVariable Long professionalId) {
        List<WeeklySchedule> schedules = scheduleRepository.findByProfessionalId(professionalId);
        return ResponseEntity.ok(schedules);
    }
}
