package ar.edu.um.turnos.domain;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class ReservationStatusTest {

    @Test
    void testEnumValuesExist() {
        assertNotNull(ReservationStatus.valueOf("HELD"));
        assertNotNull(ReservationStatus.valueOf("WAITING_FOR_PHONE"));
        assertNotNull(ReservationStatus.valueOf("CONFIRMED"));
        assertNotNull(ReservationStatus.valueOf("CANCELLED"));
        assertNotNull(ReservationStatus.valueOf("FAILED"));
        assertNotNull(ReservationStatus.valueOf("EXPIRED"));
        assertNotNull(ReservationStatus.valueOf("INVALID"));
    }
}
