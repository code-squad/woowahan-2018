package com.woowahan.woowahan2018.dto;

public class MemberDto {
	String email;

	public MemberDto() {
	}

	public MemberDto(String email) {
		this.email = email;
	}

	public String getEmail() {
		return email;
	}

	public MemberDto setEmail(String email) {
		this.email = email;
		return this;
	}
}
