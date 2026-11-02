package ar.edu.um.catalog.web.rest;

import ar.edu.um.catalog.domain.EstadoSincronizacion;
import ar.edu.um.catalog.service.ConsultarEstadoSincronizacionUseCase;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
class EstadoSincronizacionControllerTest {

    private MockMvc mockMvc;

    @Mock
    private ConsultarEstadoSincronizacionUseCase consultarEstadoSincronizacionUseCase;

    @InjectMocks
    private EstadoSincronizacionController estadoSincronizacionController;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(estadoSincronizacionController).build();
    }

    @Test
    void debeRetornarEstadoDeSincronizacionExitosamente() throws Exception {
        EstadoSincronizacion mockEstado = EstadoSincronizacion.builder()
                .currentVersion(7L)
                .status("OK")
                .build();

        when(consultarEstadoSincronizacionUseCase.consultarEstado()).thenReturn(mockEstado);

        mockMvc.perform(get("/api/synchronization/status")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.currentVersion").value(7))
                .andExpect(jsonPath("$.status").value("OK"));
    }
}