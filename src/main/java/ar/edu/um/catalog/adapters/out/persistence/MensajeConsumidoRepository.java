package ar.edu.um.catalog.adapters.out.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MensajeConsumidoRepository extends JpaRepository<MensajeConsumidoEntity, String> {
    boolean existsByIdMensaje(String idMensaje);
}