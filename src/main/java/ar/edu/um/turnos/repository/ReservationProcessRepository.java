package ar.edu.um.turnos.repository;

import ar.edu.um.turnos.domain.ReservationProcess;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ReservationProcessRepository extends JpaRepository<ReservationProcess, Long> {

    Optional<ReservationProcess> findByReservationProcessId(String reservationProcessId);

    Optional<ReservationProcess> findByHoldId(String holdId);
    
    java.util.List<java.util.List<ReservationProcess>> findByExternalPatientId(String externalPatientId);
}
