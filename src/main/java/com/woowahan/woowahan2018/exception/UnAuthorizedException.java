package com.woowahan.woowahan2018.exception;

public class UnAuthorizedException extends RuntimeException {
    public UnAuthorizedException() {
        super("AUTHORIZATION.NOT_ALLOWED");
    }
}