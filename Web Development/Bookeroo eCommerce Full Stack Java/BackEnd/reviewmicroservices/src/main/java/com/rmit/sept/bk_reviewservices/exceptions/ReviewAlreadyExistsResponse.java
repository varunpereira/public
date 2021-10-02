package com.rmit.sept.bk_reviewservices.exceptions;

public class ReviewAlreadyExistsResponse {
    private String message;

    public ReviewAlreadyExistsResponse(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
