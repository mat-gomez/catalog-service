package ar.edu.um.turnos.repository;

import ar.edu.um.turnos.domain.ReservationProcess;
import ar.edu.um.turnos.domain.ReservationStatus;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ReservationProcessRepositoryTest {

    @Mock
    private ReservationProcessRepository repository;

    @Test
    void testFindByReservationProcessIdMock() {
        ReservationProcess process = ReservationProcess.builder()
                .reservationProcessId("process-test-99")
                .holdId("hold-test-99")
                .professionalId(15L)
                .appointmentDate(LocalDate.now().plusDays(1))
                .startTime(LocalTime.of(10, 0))
                .externalPatientId("user-100")
                .status(ReservationStatus.HELD)
                .expiresAt(Instant.now().plusSeconds(600))
                .createdAt(Instant.now())
                .build();

        when(repository.findByReservationProcessId("process-test-99")).thenReturn(Optional.of(process));

        Optional<ReservationProcess> found = repository.findByReservationProcessId("process-test-99");

        assertTrue(found.isPresent());
        assertEquals("hold-test-99", found.get().getHoldId());
        assertEquals(ReservationStatus.HELD, found.get().getStatus());
        verify(repository, times(1)).findByReservationProcessId("process-test-99");
    }
}
