package de.altenerding.biber.pinkie.business.rest.entity;

public class ApiError {

    private ResponseCode responseCode;
    private String message;
    private int statusCode;

    public ApiError(ResponseCode responseCode) {
        this.responseCode = responseCode;
        this.message = responseCode.getMessage();
        this.statusCode = responseCode.getStatus().getStatusCode();
    }

    public ApiError(String message, int statusCode) {
        this.message = message;
        this.statusCode = statusCode;
    }

    public ApiError(ResponseCode responseCode, String message) {
        this.responseCode = responseCode;
        this.message = message;
        this.statusCode = responseCode.getStatus().getStatusCode();
    }

    public ResponseCode getResponseCode() {
        return responseCode;
    }

    public void setResponseCode(ResponseCode responseCode) {
        this.responseCode = responseCode;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }
}
