package com.woowahan.woowahan2018.dto;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.Objects;

public class CommonResponse {

	private ResponseStatus status;
	private String field;
	private String message;
	private Object content;

	public CommonResponse() {
	}

	private CommonResponse(ResponseStatus status, String message) {
		this.status = status;
		this.field = "";
		this.message = message;
	}

	private CommonResponse(ResponseStatus status, String field, String message) {
		this.status = status;
		this.field = field;
		this.message = message;
	}

	private CommonResponse(ResponseStatus status, String message, Object content) {
		this.status = status;
		this.message = message;
		this.content = content;
	}

	public ResponseStatus getStatus() {
		return status;
	}

	public String getField() {
		return field;
	}

	public String getMessage() {
		return message;
	}

	public static CommonResponse success(String message) {
		return new CommonResponse(ResponseStatus.OK, message);
	}

	public static CommonResponse error(String message) {
		return new CommonResponse(ResponseStatus.FAIL, message);
	}

	public static CommonResponse success(String field, String message) {
		return new CommonResponse(ResponseStatus.OK, field, message);
	}

	public static CommonResponse error(String field, String message) {
		return new CommonResponse(ResponseStatus.FAIL, field, message);
	}

	public static CommonResponse success(String message, Object content) {
		return new CommonResponse(ResponseStatus.OK, message, content);
	}

	public static CommonResponse error(String message, Object content) {
		return new CommonResponse(ResponseStatus.FAIL, message, content);
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		CommonResponse response = (CommonResponse) o;
		return status == response.status &&
				Objects.equals(field, response.field) &&
				Objects.equals(message, response.message) &&
				Objects.equals(content, response.content);
	}

	@Override
	public int hashCode() {
		return Objects.hash(status, field, message, content);
	}

	@Override
	public String toString() {
		return toJsonString();
	}

	public String toJsonString() {
		ObjectMapper mapper = new ObjectMapper();
		String result;
		try {
			result = mapper.writeValueAsString(this);
		} catch (JsonProcessingException e) {
			result = "";
		}
		return result;
	}
}
