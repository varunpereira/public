package com.rmit.sept.bk_reviewservices.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class ReviewAlreadyExistsException extends RuntimeException{
    public ReviewAlreadyExistsException(String message){
        super(message);
    }
}
