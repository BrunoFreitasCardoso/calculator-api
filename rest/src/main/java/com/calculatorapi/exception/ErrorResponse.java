package com.calculatorapi.exception;

import org.springframework.http.HttpStatus;

public class ErrorResponse {
    private String error;
    private String message;

    int status;

    public ErrorResponse(String error, String message, HttpStatus status) {
        this.error = error;
        this.message = message;
        this.status = status.value();
    }

    public String getError() {
        return error;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public void setError(String error) {
        this.error = error;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
