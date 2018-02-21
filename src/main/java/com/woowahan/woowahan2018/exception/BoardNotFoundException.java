package com.woowahan.woowahan2018.exception;

public class BoardNotFoundException extends Throwable {

    public BoardNotFoundException() {
        super("보드를 찾을 수 없습니다.");
    }

}
