package com.rmit.sept.bk_orderservices.exceptions;

public class OrderAlreadyExistsResponse {
    private String message;

    public OrderAlreadyExistsResponse(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
