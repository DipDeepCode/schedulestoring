package ru.ddc.schedulestoring.exception;

public class UpdateEntityException extends RuntimeException {
    public UpdateEntityException() {
        super();
    }

    public UpdateEntityException(String message) {
        super(message);
    }
}
