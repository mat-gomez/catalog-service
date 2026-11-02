package ar.edu.um.catalog.service.exception;

public class FuenteCatalogoNoDisponibleException extends RuntimeException {

    public FuenteCatalogoNoDisponibleException(String message) {
        super(message);
    }

    public FuenteCatalogoNoDisponibleException(String message, Throwable cause) {
        super(message, cause);
    }
}