package ar.edu.um.catalog.web.rest.dto;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class EstadoSincronizacionResponseTest {

    @Test
    void testBuilderAndGetters() {
        EstadoSincronizacionResponse response = EstadoSincronizacionResponse.builder()
                .currentVersion(5L)
                .oldestAvailableVersion(1L)
                .status("SYNCED")
                .build();

        assertThat(response).isNotNull();
        assertThat(response.getCurrentVersion()).isEqualTo(5L);
        assertThat(response.getOldestAvailableVersion()).isEqualTo(1L);
        assertThat(response.getStatus()).isEqualTo("SYNCED");
    }

    @Test
    void testBuilderModification() {
        EstadoSincronizacionResponse response = EstadoSincronizacionResponse.builder()
                .currentVersion(10L)
                .oldestAvailableVersion(2L)
                .status("NOT_SYNCED")
                .build();

        assertThat(response.getCurrentVersion()).isEqualTo(10L);
        assertThat(response.getOldestAvailableVersion()).isEqualTo(2L);
        assertThat(response.getStatus()).isEqualTo("NOT_SYNCED");
    }
}