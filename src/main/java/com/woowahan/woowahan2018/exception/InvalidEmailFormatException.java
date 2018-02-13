package com.woowahan.woowahan2018.exception;

public class InvalidEmailFormatException extends RuntimeException {

	public InvalidEmailFormatException() {
		super();
	}

	public InvalidEmailFormatException(String message) {
		super(message);
	}
}
