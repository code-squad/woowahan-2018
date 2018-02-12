package com.woowahan.woowahan2018.dto;

public class CommonResponse {

    private String status;
    private String message;

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

    @Override
    public String toString() {
        return "CommonResponse{" +
                "status='" + status + '\'' +
                ", message='" + message + '\'' +
                '}';
    }

    public static CommonResponse success(String message) {
        return new CommonResponse("OK", message);
    }

    public static CommonResponse error(String message) {
        return new CommonResponse("FAIL", message);
    }
}
