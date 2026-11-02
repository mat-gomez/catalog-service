package ar.edu.um.catalog.adapters.out.persistence;

import org.junit.jupiter.api.Test;
import java.time.OffsetDateTime;

import static org.assertj.core.api.Assertions.assertThat;

class MensajeConsumidoEntityTest {

    @Test
    void debeCrearEntidadConBuilderYVerificarGettersSetters() {
        String idEsperado = "msg-test-01";
        String topicoEsperado = "catedra.catalog.topic";
        String estadoEsperado = "PROCESADO";
        OffsetDateTime fechaEsperada = OffsetDateTime.now();

        MensajeConsumidoEntity mensaje = MensajeConsumidoEntity.builder()
                .idMensaje(idEsperado)
                .topico(topicoEsperado)
                .estado(estadoEsperado)
                .fechaProcesamiento(fechaEsperada)
                .build();

        assertThat(mensaje.getIdMensaje()).isEqualTo(idEsperado);
        assertThat(mensaje.getTopico()).isEqualTo(topicoEsperado);
        assertThat(mensaje.getEstado()).isEqualTo(estadoEsperado);
        assertThat(mensaje.getFechaProcesamiento()).isEqualTo(fechaEsperada);

        mensaje.setIdMensaje("msg-test-02");
        mensaje.setTopico("catedra.catalog.otro");
        mensaje.setEstado("IGNORADO");
        OffsetDateTime nuevaFecha = fechaEsperada.plusDays(1);
        mensaje.setFechaProcesamiento(nuevaFecha);

        assertThat(mensaje.getIdMensaje()).isEqualTo("msg-test-02");
        assertThat(mensaje.getTopico()).isEqualTo("catedra.catalog.otro");
        assertThat(mensaje.getEstado()).isEqualTo("IGNORADO");
        assertThat(mensaje.getFechaProcesamiento()).isEqualTo(nuevaFecha);
    }

    @Test
    void debeProbarConstructoresVacioYConTodosLosParametros() {
        MensajeConsumidoEntity mensajeVacio = new MensajeConsumidoEntity();
        assertThat(mensajeVacio).isNotNull();

        OffsetDateTime ahora = OffsetDateTime.now();
        MensajeConsumidoEntity mensajeCompleto = new MensajeConsumidoEntity(
                "msg-all-args",
                "catedra.topic.test",
                "PROCESADO",
                ahora
        );

        assertThat(mensajeCompleto.getIdMensaje()).isEqualTo("msg-all-args");
        assertThat(mensajeCompleto.getTopico()).isEqualTo("catedra.topic.test");
        assertThat(mensajeCompleto.getEstado()).isEqualTo("PROCESADO");
        assertThat(mensajeCompleto.getFechaProcesamiento()).isEqualTo(ahora);
    }
}