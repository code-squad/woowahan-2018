package com.woowahan.woowahan2018.exception;

public class DuplicatedEmailException extends RuntimeException {

    public DuplicatedEmailException() {
        super();
    }

    public DuplicatedEmailException(String message) {
        super(message);
    }
}
