package com.woowahan.woowahan2018.exception;

public class WrongPasswordException extends Exception {
    public WrongPasswordException() {
        super("PASSWORD.WRONG");
    }
}
