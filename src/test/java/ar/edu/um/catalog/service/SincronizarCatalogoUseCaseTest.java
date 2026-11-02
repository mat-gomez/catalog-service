package ar.edu.um.catalog.service;

import ar.edu.um.catalog.domain.CambiosCatalogo;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class SincronizarCatalogoUseCaseTest {

    @Mock
    private ConsultarEstadoSincronizacionUseCase consultarEstadoUseCase;

    @Mock
    private AplicarSnapshotCatalogoUseCase aplicarSnapshotUseCase;

    @InjectMocks
    private SincronizarCatalogoUseCase useCase;

    @Test
    void testSincronizarConCambios() {
        CambiosCatalogo cambios = CambiosCatalogo.builder()
                .targetVersion(2L)
                .hasChanges(true)
                .build();

        boolean resultado = useCase.sincronizarSiHayCambios(cambios);
        assertTrue(resultado);
    }

    @Test
    void testSincronizarSinCambios() {
        CambiosCatalogo cambios = CambiosCatalogo.builder()
                .targetVersion(1L)
                .hasChanges(false)
                .build();

        boolean resultado = useCase.sincronizarSiHayCambios(cambios);
        assertFalse(resultado);
    }
}