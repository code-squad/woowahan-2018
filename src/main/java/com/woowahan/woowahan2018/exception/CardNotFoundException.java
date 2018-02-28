package com.woowahan.woowahan2018.exception;

public class CardNotFoundException extends Exception {
    public CardNotFoundException() {
        super("CARD.NOT_FOUND");
    }
}
