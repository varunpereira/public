package com.rmit.sept.bk_paymentservices.web;

import com.rmit.sept.bk_paymentservices.model.*;
import com.rmit.sept.bk_paymentservices.repository.*;
import com.rmit.sept.bk_paymentservices.services.MapValidationErrorService;
import com.rmit.sept.bk_paymentservices.services.PaymentService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import com.paypal.api.payments.Links;
import com.paypal.api.payments.Payment;
import com.paypal.base.rest.PayPalRESTException;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import java.io.IOException;
import java.util.List;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/api/payment")
public class PaymentController {
	
	@Autowired
    private MapValidationErrorService mapValidationErrorService;
	
	@Autowired
    private PaymentService paymentService;
	
	@Autowired
    private PaymentRepository paymentRespository;
	
	public static final String SUCCESS_URL = "pay/success";
	public static final String CANCEL_URL = "pay/cancel";
	public String username = "";

	// adds payment
    @PostMapping("/addPayment")
    public ResponseEntity<?> addPayment(@Valid @RequestBody com.rmit.sept.bk_paymentservices.model.Payment payment, BindingResult result){
        ResponseEntity<?> errorMap = mapValidationErrorService.MapValidationService(result);
        if (errorMap != null) {
            return errorMap;
        }

        com.rmit.sept.bk_paymentservices.model.Payment newPayment = paymentService.savePayment(payment);

        return new ResponseEntity<com.rmit.sept.bk_paymentservices.model.Payment>(newPayment, HttpStatus.CREATED);
    }
	
	
    // finds user transactions for history
    @PostMapping("/findTranactions/{username}")
    public List<com.rmit.sept.bk_paymentservices.model.Payment> findReviewsByBook(@PathVariable(value = "username") String username) {
        return paymentService.findTransactions(username);
    }
    
    // finds all transaction for history orer by datetime
    @PostMapping("/findAllTranactions")
    public List<com.rmit.sept.bk_paymentservices.model.Payment> findAllTranactions() {
        return paymentService.transactionHistory();
    }
	
	// verifies if a payment exists
    @PostMapping("/paymentExists/{id}")
    public ResponseEntity<?> checkPaymentExists(@PathVariable(value = "id") Long id){
        if(paymentService.paymentExists(id)){
            return new ResponseEntity<String>("Exists", HttpStatus.OK);
        } else {
            return new ResponseEntity<String>("Does not exist", HttpStatus.OK);
        }
    }
	
	// Paypal requests
	// Change SUCCESS_URL to front end URL
	@PostMapping("/pay/{id}/{username}")
	public ResponseEntity<?> paypalPayment(@ModelAttribute("payment") Payment payment, @PathVariable(value = "id") Long id, @PathVariable(value = "username") String username){
		if(paymentService.paymentExists(id)){
				try {
				com.rmit.sept.bk_paymentservices.model.Payment currentPayment = paymentRespository.findByPaymentid(id);
				this.username = username;
				Payment paymentProcessed = paymentService.createPayment(currentPayment.getPrice(), currentPayment.getCurrency(), currentPayment.getMethod(), currentPayment.getIntent(), currentPayment.getDescription(), "http://localhost:1008/api/payment/"+CANCEL_URL, "http://localhost:3000/"+this.username+"/paymentresult/success");

				for(Links link:paymentProcessed.getLinks()){
					if(link.getRel().equals("approval_url")){
						return new ResponseEntity<String>(link.getHref(), HttpStatus.OK);
					}
				}
			} catch (PayPalRESTException e){
				e.printStackTrace();
			}
		} else {
			return new ResponseEntity<String>("Payment with id " + id + " does not exist", HttpStatus.OK);
		}
		return null;
	}
	
	@GetMapping(value = CANCEL_URL)
	public ResponseEntity<?> cancelPay(){
		return new ResponseEntity<String>("Payment Canceled", HttpStatus.OK);
	}
	
	@GetMapping("/success")
	public ResponseEntity<?> successPay(@RequestParam("paymentId") String paymentId, @RequestParam("PayerID") String payerID){
		try {
			Payment payment = paymentService.executePayment(paymentId, payerID);

			if (payment.getState().equals("approved")){
				return new ResponseEntity<String>(this.username, HttpStatus.OK);
			}
		} catch (PayPalRESTException e){
			System.out.println(e.getMessage());
		}
		return new ResponseEntity<String>(this.username, HttpStatus.OK);
	}
	
	// For refund
	
	// refunds a payment
    @PatchMapping("/refund/{group}")
    public ResponseEntity<?> refundPaymentByGroup(@PathVariable(value = "group") Integer group){
    	paymentService.paymentRefund(group); 
    	
    	return new ResponseEntity<Boolean>(true, HttpStatus.OK);
    }   
    
    // Download csv file for Basic Transaction List
    @GetMapping("/transactionListBasic")
    public void getBookBasic(HttpServletResponse servletResponse) throws IOException  {
    	servletResponse.setContentType("text/csv");
        servletResponse.addHeader("Content-Disposition","attachment; filename=\"basicTransactionList.csv\"");
        paymentService.quickTransactionListCsv(servletResponse.getWriter());
    }
    
    // Download csv file for Detailed Transaction List
    @GetMapping("/transactionListDetailed")
    public void getBookDetailed(HttpServletResponse servletResponse) throws IOException  {
    	servletResponse.setContentType("text/csv");
        servletResponse.addHeader("Content-Disposition","attachment; filename=\"detailedTransactionList.csv\"");
        paymentService.detailedTransactionListCsv(servletResponse.getWriter());
    }
}
