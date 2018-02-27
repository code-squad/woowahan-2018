package com.woowahan.woowahan2018.exception;

public class ExistMemberExeption extends Exception {
	public ExistMemberExeption() {
		super("이미 보드에 존재하는 멤버입니다.");
	}
}