package ar.edu.um.catalog.adapters.out.persistence;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.Instant;
import java.time.LocalTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class WeeklyScheduleRepositoryTest {

    @Autowired
    private WeeklyScheduleRepository repository;

    @Test
    void testFindByProfessionalId() {
        Instant now = Instant.now();
        WeeklyScheduleEntity schedule = WeeklyScheduleEntity.builder()
                .id(1L)
                .professionalId(10L)
                .dayOfWeek("MONDAY")
                .startTime(LocalTime.of(9, 0))
                .endTime(LocalTime.of(17, 0))
                .slotDurationMinutes(30)
                .enabled(true)
                .createdAt(now)
                .updatedAt(now)
                .build();

        repository.save(schedule);

        List<WeeklyScheduleEntity> results = repository.findByProfessionalId(10L);
        List<WeeklyScheduleEntity> emptyResults = repository.findByProfessionalId(99L);

        assertEquals(1, results.size());
        assertEquals(10L, results.get(0).getProfessionalId());
        assertEquals("MONDAY", results.get(0).getDayOfWeek());
        assertTrue(emptyResults.isEmpty());
    }
}