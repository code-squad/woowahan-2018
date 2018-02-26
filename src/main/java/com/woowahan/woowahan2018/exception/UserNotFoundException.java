package com.woowahan.woowahan2018.exception;

public class UserNotFoundException extends Exception {
    public UserNotFoundException() {
        super("유저를 찾지 못했습니다.");
    }
}
