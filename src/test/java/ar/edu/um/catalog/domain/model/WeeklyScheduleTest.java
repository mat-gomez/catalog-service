package ar.edu.um.catalog.domain.model;

import ar.edu.um.catalog.domain.WeeklySchedule;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class WeeklyScheduleTest {

    @Test
    void testWeeklyScheduleInstantiation() {
        WeeklySchedule schedule = new WeeklySchedule();
        assertNotNull(schedule);
    }
}
