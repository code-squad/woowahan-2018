package com.woowahan.woowahan2018.dto;

import java.util.Objects;

public class CommonResponse {

    private String status;
    private String message;

    public CommonResponse() {
    }

    private CommonResponse(String status, String message) {
        this.status = status;
        this.message = message;
    }

    public String getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }

    public static CommonResponse success(String message) {
        return new CommonResponse("OK", message);
    }

    public static CommonResponse error(String message) {
        return new CommonResponse("FAIL", message);
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
}
