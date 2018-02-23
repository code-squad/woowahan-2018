package com.woowahan.woowahan2018.exception;

public class UnAuthorizedException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    public UnAuthorizedException() {
        super();
    }

    public UnAuthorizedException(String message, Throwable cause, boolean enableSuppression,
                                 boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

    public UnAuthorizedException(String message, Throwable cause) {
        super(message, cause);
    }

    public UnAuthorizedException(String message) {
        super(message);
    }

    public UnAuthorizedException(Throwable cause) {
        super(cause);
    }
}