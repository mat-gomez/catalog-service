package ar.edu.um.catalog.adapters.out.persistence;

import org.junit.jupiter.api.Test;
import java.time.Instant;
import java.time.LocalTime;

import static org.junit.jupiter.api.Assertions.*;

class WeeklyScheduleEntityTest {

    @Test
    void testGettersAndSetters() {
        WeeklyScheduleEntity entity = new WeeklyScheduleEntity();
        Long id = 1L;
        Long professionalId = 10L;
        String dayOfWeek = "MONDAY";
        LocalTime startTime = LocalTime.of(8, 0);
        LocalTime endTime = LocalTime.of(16, 0);
        Integer slotDurationMinutes = 30;
        Boolean enabled = true;
        Instant now = Instant.now();

        entity.setId(id);
        entity.setProfessionalId(professionalId);
        entity.setDayOfWeek(dayOfWeek);
        entity.setStartTime(startTime);
        entity.setEndTime(endTime);
        entity.setSlotDurationMinutes(slotDurationMinutes);
        entity.setEnabled(enabled);
        entity.setCreatedAt(now);
        entity.setUpdatedAt(now);

        assertEquals(id, entity.getId());
        assertEquals(professionalId, entity.getProfessionalId());
        assertEquals(dayOfWeek, entity.getDayOfWeek());
        assertEquals(startTime, entity.getStartTime());
        assertEquals(endTime, entity.getEndTime());
        assertEquals(slotDurationMinutes, entity.getSlotDurationMinutes());
        assertEquals(enabled, entity.getEnabled());
        assertEquals(now, entity.getCreatedAt());
        assertEquals(now, entity.getUpdatedAt());
    }

    @Test
    void testAllArgsConstructor() {
        Instant now = Instant.now();
        LocalTime startTime = LocalTime.of(9, 0);
        LocalTime endTime = LocalTime.of(18, 0);
        WeeklyScheduleEntity entity = new WeeklyScheduleEntity(1L, 10L, "TUESDAY", startTime, endTime, 45, false, now, now);

        assertNotNull(entity);
        assertEquals(1L, entity.getId());
        assertEquals(10L, entity.getProfessionalId());
        assertEquals("TUESDAY", entity.getDayOfWeek());
        assertEquals(startTime, entity.getStartTime());
        assertEquals(endTime, entity.getEndTime());
        assertEquals(45, entity.getSlotDurationMinutes());
        assertFalse(entity.getEnabled());
        assertEquals(now, entity.getCreatedAt());
        assertEquals(now, entity.getUpdatedAt());
    }

    @Test
    void testBuilder() {
        Instant now = Instant.now();
        LocalTime startTime = LocalTime.of(10, 0);
        LocalTime endTime = LocalTime.of(14, 0);
        WeeklyScheduleEntity entity = WeeklyScheduleEntity.builder()
                .id(2L)
                .professionalId(20L)
                .dayOfWeek("FRIDAY")
                .startTime(startTime)
                .endTime(endTime)
                .slotDurationMinutes(60)
                .enabled(true)
                .createdAt(now)
                .updatedAt(now)
                .build();

        assertNotNull(entity);
        assertEquals(2L, entity.getId());
        assertEquals(20L, entity.getProfessionalId());
        assertEquals("FRIDAY", entity.getDayOfWeek());
        assertEquals(startTime, entity.getStartTime());
        assertEquals(endTime, entity.getEndTime());
        assertEquals(60, entity.getSlotDurationMinutes());
        assertTrue(entity.getEnabled());
        assertEquals(now, entity.getCreatedAt());
        assertEquals(now, entity.getUpdatedAt());
    }
}