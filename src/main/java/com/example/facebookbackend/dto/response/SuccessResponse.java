package com.example.facebookbackend.dto.response;

import java.util.List;

public class SuccessResponse {
    Boolean isSuccess;

    public Boolean getSuccess() {
        return isSuccess;
    }

    public void setSuccess(Boolean success) {
        isSuccess = success;
    }

    public List<Object> getErrors() {
        return errors;
    }

    public void setErrors(List<Object> errors) {
        this.errors = errors;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    List<Object> errors;
    String message;

    public SuccessResponse() {
        isSuccess = true;
    }

    public SuccessResponse(Boolean isSuccess, List<Object> errors, String message) {
        this.isSuccess = isSuccess;
        this.errors = errors;
        this.message = message;
    }
}
