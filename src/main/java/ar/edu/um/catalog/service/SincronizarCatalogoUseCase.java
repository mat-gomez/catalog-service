package ar.edu.um.catalog.service;

import ar.edu.um.catalog.domain.CambiosCatalogo;
import org.springframework.stereotype.Service;

@Service
public class SincronizarCatalogoUseCase {

    private final ConsultarEstadoSincronizacionUseCase consultarEstadoUseCase;
    private final AplicarSnapshotCatalogoUseCase aplicarSnapshotUseCase;

    public SincronizarCatalogoUseCase(ConsultarEstadoSincronizacionUseCase consultarEstadoUseCase,
                                      AplicarSnapshotCatalogoUseCase aplicarSnapshotUseCase) {
        this.consultarEstadoUseCase = consultarEstadoUseCase;
        this.aplicarSnapshotUseCase = aplicarSnapshotUseCase;
    }

    public boolean sincronizarSiHayCambios(CambiosCatalogo cambios) {
        if (cambios == null || Boolean.FALSE.equals(cambios.getHasChanges())) {
            return false;
        }
        return true;
    }
}