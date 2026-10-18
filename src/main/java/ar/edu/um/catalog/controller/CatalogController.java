package ar.edu.um.catalog.controller;

import ar.edu.um.catalog.domain.Professional;
import ar.edu.um.catalog.domain.ProfessionalCategory;
import ar.edu.um.catalog.domain.WeeklySchedule;
import ar.edu.um.catalog.repository.ProfessionalCategoryRepository;
import ar.edu.um.catalog.repository.ProfessionalRepository;
import ar.edu.um.catalog.repository.WeeklyScheduleRepository;
import ar.edu.um.catalog.service.CatalogSyncService;
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
    private final CatalogSyncService catalogSyncService;

    // Endpoint para forzar o reintentar la sincronizacion completa local
    @PostMapping("/sync")
    public ResponseEntity<String> triggerSync() {
        catalogSyncService.executeFullSnapshotSync();
        return ResponseEntity.ok("Sincronizacion completa ejecutada correctamente.");
    }

    // Listar categorias locales
    @GetMapping("/categories")
    public ResponseEntity<List<ProfessionalCategory>> getAllCategories() {
        return ResponseEntity.ok(categoryRepository.findAll());
    }

    // Buscar profesionales utilizando filtros locales obligatorios (categoria, nombre, estado)
    @GetMapping("/professionals")
    public ResponseEntity<List<Professional>> searchProfessionals(
            @RequestParam(required = false) Long categoryId,
            @RequestParam(required = false) String name,
            @RequestParam(required = false) Boolean enabled) {
        List<Professional> professionals = professionalRepository.findByFilters(categoryId, name, enabled);
        return ResponseEntity.ok(professionals);
    }

    // Consultar horarios semanales de un profesional especifico
    @GetMapping("/professionals/{professionalId}/schedules")
    public ResponseEntity<List<WeeklySchedule>> getProfessionalSchedules(@PathVariable Long professionalId) {
        List<WeeklySchedule> schedules = scheduleRepository.findByProfessionalId(professionalId);
        return ResponseEntity.ok(schedules);
    }
}
