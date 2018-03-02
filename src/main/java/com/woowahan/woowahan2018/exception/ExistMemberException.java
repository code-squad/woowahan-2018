package com.woowahan.woowahan2018.exception;

public class ExistMemberException extends Exception {
	public ExistMemberException() {
		super("MEMBER.ALREADY_EXISTS");
	}
}