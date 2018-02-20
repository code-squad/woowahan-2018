package com.woowahan.woowahan2018.dto;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.Objects;

public class CommonResponse {

    private ResponseStatus status;
    private String message;
    private Object content;

    public CommonResponse() {
    }

    private CommonResponse(ResponseStatus status, String message) {
        this.status = status;
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

    public String getMessage() {
        return message;
    }

    public static CommonResponse success(String message) {
        return new CommonResponse(ResponseStatus.OK, message);
    }

    public static CommonResponse error(String message) {
        return new CommonResponse(ResponseStatus.FAIL, message);
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
        return Objects.equals(status, response.status) &&
                Objects.equals(message, response.message);
    }

    @Override
    public int hashCode() {
        return Objects.hash(status, message);
    }

    @Override
    public String toString() {
        return "CommonResponse{" +
                "status='" + status + '\'' +
                ", message='" + message + '\'' +
                '}';
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
