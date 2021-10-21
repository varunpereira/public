package com.rmit.sept.bk_paymentservices.exceptions;

public class PaymentAlreadyExistsResponse {
	 private String message;

	    public PaymentAlreadyExistsResponse(String message) {
	        this.message = message;
	    }

	    public String getMessage() {
	        return message;
	    }

	    public void setMessage(String message) {
	        this.message = message;
	    }
}
