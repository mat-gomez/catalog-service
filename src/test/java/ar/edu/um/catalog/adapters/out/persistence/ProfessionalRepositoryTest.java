package ar.edu.um.catalog.adapters.out.persistence;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.Instant;
import java.time.LocalTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ProfessionalRepositoryTest {

    @Autowired
    private ProfessionalRepository professionalRepository;

    @Autowired
    private WeeklyScheduleRepository scheduleRepository;

    @BeforeEach
    void setUp() {
        scheduleRepository.deleteAll();
        professionalRepository.deleteAll();
        Instant now = Instant.now();

        ProfessionalEntity p1 = ProfessionalEntity.builder()
                .id(1L)
                .categoryId(10L)
                .firstName("Juan")
                .lastName("Pérez")
                .enabled(true)
                .createdAt(now)
                .updatedAt(now)
                .build();

        ProfessionalEntity p2 = ProfessionalEntity.builder()
                .id(2L)
                .categoryId(20L)
                .firstName("María")
                .lastName("Gómez")
                .enabled(false)
                .createdAt(now)
                .updatedAt(now)
                .build();

        professionalRepository.saveAll(List.of(p1, p2));

        WeeklyScheduleEntity schedule = WeeklyScheduleEntity.builder()
                .id(1L)
                .professionalId(1L)
                .dayOfWeek("WEDNESDAY")
                .startTime(LocalTime.of(9, 0))
                .endTime(LocalTime.of(12, 0))
                .slotDurationMinutes(30)
                .enabled(true)
                .createdAt(now)
                .updatedAt(now)
                .build();

        scheduleRepository.save(schedule);
    }

    @Test
    void testFindByFilters_NoFilters() {
        List<ProfessionalEntity> results = professionalRepository.findByFilters(null, null, null, null);
        assertEquals(2, results.size());
    }

    @Test
    void testFindByFilters_ByCategoryId() {
        List<ProfessionalEntity> results = professionalRepository.findByFilters(10L, null, null, null);
        assertEquals(1, results.size());
        assertEquals("Juan", results.get(0).getFirstName());
    }

    @Test
    void testFindByFilters_ByName() {
        List<ProfessionalEntity> results = professionalRepository.findByFilters(null, "mar", null, null);
        assertEquals(1, results.size());
        assertEquals("María", results.get(0).getFirstName());
    }

    @Test
    void testFindByFilters_ByEnabled() {
        List<ProfessionalEntity> results = professionalRepository.findByFilters(null, null, true, null);
        assertEquals(1, results.size());
        assertTrue(results.get(0).getEnabled());
    }

    @Test
    void testFindByFilters_ByDayOfWeek_NotFound() {
        List<ProfessionalEntity> results = professionalRepository.findByFilters(null, null, null, "MONDAY");
        assertEquals(0, results.size());
    }

    @Test
    void testFindByFilters_ByDayOfWeek_Found() {
        List<ProfessionalEntity> results = professionalRepository.findByFilters(null, null, null, "WEDNESDAY");
        assertEquals(1, results.size());
        assertEquals("Juan", results.get(0).getFirstName());
    }
}