package com.woowahan.woowahan2018.exception;

public class UserNotFoundException extends Exception {
    public UserNotFoundException() {
        super("USER.NOT_FOUND");
    }
}
