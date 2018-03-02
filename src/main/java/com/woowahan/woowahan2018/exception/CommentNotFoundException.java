package com.woowahan.woowahan2018.exception;

public class CommentNotFoundException extends Exception {
    public CommentNotFoundException() {
        super("COMMENT.NOT_FOUND");
    }
}
