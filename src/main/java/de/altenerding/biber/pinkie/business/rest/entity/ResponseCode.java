package de.altenerding.biber.pinkie.business.rest.entity;

import javax.ws.rs.core.Response;

import static javax.ws.rs.core.Response.Status.UNAUTHORIZED;

public enum ResponseCode {
    TOKEN_INVALID("Token invalid or expired", UNAUTHORIZED),
    ;

    private final String message;
    private final Response.Status status;

    ResponseCode(String message, Response.Status status) {
        this.message = message;
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public Response.Status getStatus() {
        return status;
    }
}
