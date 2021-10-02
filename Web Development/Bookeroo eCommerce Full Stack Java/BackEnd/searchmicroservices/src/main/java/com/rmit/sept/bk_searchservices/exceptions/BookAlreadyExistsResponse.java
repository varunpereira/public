package com.rmit.sept.bk_searchservices.exceptions;

public class BookAlreadyExistsResponse {

    private String message;

    public BookAlreadyExistsResponse(String message) {
        this.message = message;
    }

    public String getIsbn() {
        return message;
    }

    public void setIsbn(String message) {
        this.message = message;
    }
}