package com.spbstu.StudMess.exception;

public class NonUniqueValueException extends RuntimeException {
    public NonUniqueValueException(String message) {
        super(message);
    }

    public NonUniqueValueException(String message, Throwable cause) {
        super(message, cause);
    }
}
