package com.woowahan.woowahan2018.exception;

public class DeckNotFoundException extends Exception {
    public DeckNotFoundException() {
        super("덱을 찾을 수 없습니다.");
    }
}
