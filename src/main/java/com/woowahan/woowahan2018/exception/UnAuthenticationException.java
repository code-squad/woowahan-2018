package com.woowahan.woowahan2018.exception;

public class UnAuthenticationException extends Exception {

    public UnAuthenticationException() {
        super("AUTHENTICATION.NOT_ALLOWED");
    }
}
