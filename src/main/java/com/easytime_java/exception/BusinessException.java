package com.easytime_java.exception;

/**
 * Excepción sencilla para errores de negocio/validación.
 */
public class BusinessException extends RuntimeException {
    public BusinessException(String message) {
        super(message);
    }
}