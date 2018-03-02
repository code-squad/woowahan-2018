package com.woowahan.woowahan2018.exception;

public class ExistMemberExeption extends Exception {
	public ExistMemberExeption() {
		super("MEMBER.ALREADY_EXISTS");
	}
}