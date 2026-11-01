package ar.edu.um.catalog.service.exception;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class FuenteCatalogoNoDisponibleExceptionTest {

    @Test
    void testExceptionWithMessage() {
        String errorMessage = "La fuente del catálogo no está disponible";
        FuenteCatalogoNoDisponibleException exception = new FuenteCatalogoNoDisponibleException(errorMessage);

        assertNotNull(exception);
        assertEquals(errorMessage, exception.getMessage());
        assertNull(exception.getCause());
    }

    @Test
    void testExceptionWithMessageAndCause() {
        String errorMessage = "Error de conexión con el servicio remoto";
        Throwable cause = new RuntimeException("Timeout de red");
        FuenteCatalogoNoDisponibleException exception = new FuenteCatalogoNoDisponibleException(errorMessage, cause);

        assertNotNull(exception);
        assertEquals(errorMessage, exception.getMessage());
        assertEquals(cause, exception.getCause());
    }
}