package com.rmit.sept.bk_paymentservices.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class PaymentAlreadyExistsException extends RuntimeException{
	    public PaymentAlreadyExistsException(String message){
	        super(message);
	    }
}