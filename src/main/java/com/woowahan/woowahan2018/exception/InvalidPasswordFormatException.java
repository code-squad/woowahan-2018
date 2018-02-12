package com.woowahan.woowahan2018.exception;

public class InvalidPasswordFormatException extends RuntimeException {

    public InvalidPasswordFormatException() {
        super();
    }

    public InvalidPasswordFormatException(String message) {
        super(message);
    }
}
