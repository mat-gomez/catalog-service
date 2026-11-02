package ar.edu.um.catalog.adapters.out.persistence;

import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.time.OffsetDateTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
@ActiveProfiles("test")
@Transactional
class MensajeConsumidoRepositoryTest {

    @Autowired
    private MensajeConsumidoRepository mensajeConsumidoRepository;

    @Autowired
    private EntityManager entityManager;

    @Test
    void debeGuardarYVerificarExistenciaDeMensaje() {
        String idTest = "kafka-msg-999";
        MensajeConsumidoEntity mensaje = MensajeConsumidoEntity.builder()
                .idMensaje(idTest)
                .topico("catedra.catalog.proyecto-test")
                .estado("PROCESADO")
                .fechaProcesamiento(OffsetDateTime.now())
                .build();

        mensajeConsumidoRepository.save(mensaje);
        boolean existe = mensajeConsumidoRepository.existsByIdMensaje(idTest);

        assertThat(existe).isTrue();
    }

    @Test
    void debeRechazarIdDuplicadoParaGarantizarDeduplicacion() {
        String idDuplicado = "kafka-msg-dup";

        MensajeConsumidoEntity primerMensaje = MensajeConsumidoEntity.builder()
                .idMensaje(idDuplicado)
                .topico("catedra.catalog.proyecto-test")
                .estado("PROCESADO")
                .fechaProcesamiento(OffsetDateTime.now())
                .build();

        mensajeConsumidoRepository.saveAndFlush(primerMensaje);

        entityManager.clear();

        MensajeConsumidoEntity segundoMensaje = MensajeConsumidoEntity.builder()
                .idMensaje(idDuplicado)
                .topico("catedra.catalog.proyecto-test")
                .estado("PROCESADO")
                .fechaProcesamiento(OffsetDateTime.now())
                .build();

        assertThrows(Exception.class, () -> {
            entityManager.persist(segundoMensaje);
            entityManager.flush();
        });
    }
}