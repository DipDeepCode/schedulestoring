package ru.ddc.schedulestoring.exception;

public class DeleteEntityException extends RuntimeException {
    public DeleteEntityException() {
        super();
    }

    public DeleteEntityException(String message) {
        super(message);
    }
}
