package ar.edu.um.catalog.domain.model;

import ar.edu.um.catalog.domain.WeeklySchedule;
import org.junit.jupiter.api.Test;
import java.time.Instant;
import java.time.LocalTime;

import static org.junit.jupiter.api.Assertions.*;

class WeeklyScheduleTest {

    @Test
    void testWeeklyScheduleGettersAndSetters() {
        WeeklySchedule schedule = new WeeklySchedule();

        Long id = 1L;
        Long professionalId = 10L;
        String dayOfWeek = "MONDAY";
        LocalTime startTime = LocalTime.of(9, 0);
        LocalTime endTime = LocalTime.of(17, 0);
        Integer slotDurationMinutes = 30;
        Boolean enabled = true;
        Instant now = Instant.now();

        schedule.setId(id);
        schedule.setProfessionalId(professionalId);
        schedule.setDayOfWeek(dayOfWeek);
        schedule.setStartTime(startTime);
        schedule.setEndTime(endTime);
        schedule.setSlotDurationMinutes(slotDurationMinutes);
        schedule.setEnabled(enabled);
        schedule.setCreatedAt(now);
        schedule.setUpdatedAt(now);

        assertEquals(id, schedule.getId());
        assertEquals(professionalId, schedule.getProfessionalId());
        assertEquals(dayOfWeek, schedule.getDayOfWeek());
        assertEquals(startTime, schedule.getStartTime());
        assertEquals(endTime, schedule.getEndTime());
        assertEquals(slotDurationMinutes, schedule.getSlotDurationMinutes());
        assertEquals(enabled, schedule.getEnabled());
        assertEquals(now, schedule.getCreatedAt());
        assertEquals(now, schedule.getUpdatedAt());
    }

    @Test
    void testWeeklyScheduleAllArgsConstructor() {
        Instant now = Instant.now();
        LocalTime startTime = LocalTime.of(8, 0);
        LocalTime endTime = LocalTime.of(13, 0);
        WeeklySchedule schedule = new WeeklySchedule(1L, 10L, "TUESDAY", startTime, endTime, 60, false, now, now);

        assertNotNull(schedule);
        assertEquals(1L, schedule.getId());
        assertEquals(10L, schedule.getProfessionalId());
        assertEquals("TUESDAY", schedule.getDayOfWeek());
        assertEquals(startTime, schedule.getStartTime());
        assertEquals(endTime, schedule.getEndTime());
        assertEquals(60, schedule.getSlotDurationMinutes());
        assertFalse(schedule.getEnabled());
        assertEquals(now, schedule.getCreatedAt());
        assertEquals(now, schedule.getUpdatedAt());
    }

    @Test
    void testWeeklyScheduleBuilder() {
        Instant now = Instant.now();
        LocalTime startTime = LocalTime.of(10, 0);
        LocalTime endTime = LocalTime.of(18, 0);
        WeeklySchedule schedule = WeeklySchedule.builder()
                .id(5L)
                .professionalId(20L)
                .dayOfWeek("WEDNESDAY")
                .startTime(startTime)
                .endTime(endTime)
                .slotDurationMinutes(45)
                .enabled(true)
                .createdAt(now)
                .updatedAt(now)
                .build();

        assertNotNull(schedule);
        assertEquals(5L, schedule.getId());
        assertEquals(20L, schedule.getProfessionalId());
        assertEquals("WEDNESDAY", schedule.getDayOfWeek());
        assertEquals(startTime, schedule.getStartTime());
        assertEquals(endTime, schedule.getEndTime());
        assertEquals(45, schedule.getSlotDurationMinutes());
        assertTrue(schedule.getEnabled());
        assertEquals(now, schedule.getCreatedAt());
        assertEquals(now, schedule.getUpdatedAt());
    }
}