package ar.edu.um.turnos.domain;

import org.junit.jupiter.api.Test;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalTime;
import static org.junit.jupiter.api.Assertions.*;

class ReservationProcessTest {

    @Test
    void testReservationProcessBuilderAndGetters() {
        ReservationProcess process = ReservationProcess.builder()
                .reservationProcessId("process-123")
                .holdId("hold-456")
                .professionalId(15L)
                .appointmentDate(LocalDate.of(2026, 10, 25))
                .startTime(LocalTime.of(9, 30))
                .externalPatientId("user-42")
                .status(ReservationStatus.HELD)
                .expiresAt(Instant.now().plusSeconds(600))
                .createdAt(Instant.now())
                .build();

        assertNotNull(process);
        assertEquals("process-123", process.getReservationProcessId());
        assertEquals("hold-456", process.getHoldId());
        assertEquals(15L, process.getProfessionalId());
        assertEquals(ReservationStatus.HELD, process.getStatus());
    }
}
