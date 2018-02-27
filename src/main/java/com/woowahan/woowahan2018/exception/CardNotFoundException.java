package com.woowahan.woowahan2018.exception;

public class CardNotFoundException extends Exception {
    public CardNotFoundException() {
        super("카드를 찾을 수 없습니다.");
    }
}
