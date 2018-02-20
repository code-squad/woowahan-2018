package com.woowahan.woowahan2018.exception;

public class NoSuchBoardFoundException extends Throwable {

    public NoSuchBoardFoundException() {
        super("보드를 찾을 수 없습니다.");
    }

}
