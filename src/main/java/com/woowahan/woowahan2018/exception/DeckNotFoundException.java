package com.woowahan.woowahan2018.exception;

public class DeckNotFoundException extends Exception {
    public DeckNotFoundException() {
        super("DECK.NOT_FOUND");
    }
}
