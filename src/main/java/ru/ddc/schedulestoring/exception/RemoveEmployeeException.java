package ru.ddc.schedulestoring.exception;

public class RemoveEmployeeException extends RuntimeException {
    public RemoveEmployeeException() {
        super();
    }

    public RemoveEmployeeException(String message) {
        super(message);
    }
}
