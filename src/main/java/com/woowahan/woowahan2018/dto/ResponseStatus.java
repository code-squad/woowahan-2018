package com.woowahan.woowahan2018.dto;

public enum ResponseStatus {
	OK("OK"),
	FAIL("FAIL");

	private String message;

	ResponseStatus(String message) {
		this.message = message;
	}

	@Override
	public String toString() {
		return message;
	}
}