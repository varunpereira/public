package com.rmit.sept.bk_paymentservices.web;

import com.rmit.sept.bk_paymentservices.repository.*;
import com.rmit.sept.bk_paymentservices.services.MapValidationErrorService;
import com.rmit.sept.bk_paymentservices.services.PaymentService;

import org.apache.commons.lang3.time.DateUtils;
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
import java.util.Date;
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
	public Long id = 0L;

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
    public List<com.rmit.sept.bk_paymentservices.model.Payment> findTransactionsByUsername(@PathVariable(value = "username") String username) {
        return paymentService.findTransactions(username);
    }
    
    // finds all transaction for history order by datetime
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
				this.id = id;
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
			String paymentString = payment.toString();
			String paymentStringNoSpaces = paymentString.replaceAll("\\s", "");
			String saleID = paymentStringNoSpaces.substring(501, 518);
			if (payment.getState().equals("approved")){	
					paymentService.updateSaleID(saleID, this.id);
					return new ResponseEntity<String>(this.username, HttpStatus.OK);
				}
				
		} catch (PayPalRESTException e){
			System.out.println(e.getMessage());
		}
		return new ResponseEntity<String>(this.username, HttpStatus.OK);
	}
	  
	// For refund
	// finds user transactions by order group
	@PostMapping("/findTranactionsByOrderGroup/{ordergroup}")
	public com.rmit.sept.bk_paymentservices.model.Payment findTransactionbyOrdergroup(@PathVariable(value = "ordergroup") Integer ordergroup) {
		return paymentService.findTransactionbyOrdergroup(ordergroup);
	}
	
	// PayPal refund
	@PostMapping("/refund/{saleid}/{orderprice}")
    public ResponseEntity<?> refundPayment(@PathVariable(value = "saleid") String saleid,
    											  @PathVariable(value = "orderprice") String orderprice){
		
    	paymentService.refundSale(saleid, orderprice);
    	
    	return new ResponseEntity<Boolean>(true, HttpStatus.OK);
    }

	// refunds a payment
    @PatchMapping("/updateRefund/{group}")
    public ResponseEntity<?> updateRefund(@PathVariable(value = "group") Integer group){
    	paymentService.paymentRefund(group); 
    	
    	return new ResponseEntity<Boolean>(true, HttpStatus.OK);
    }
    
    //  refund past orders
    @PostMapping("/refundWithinTimeRange/{id}")
    public ResponseEntity<?> refundWithinTimeRange (@PathVariable(value = "id") Long id) {
        try {
        	com.rmit.sept.bk_paymentservices.model.Payment payment = paymentService.refundWithinTime(id);
            Date refundDateTime = payment.getDatetime();
            Date newDate = DateUtils.addHours(refundDateTime, 2);
            Date now = new Date();
            // checks if a refund has been made after two hours
            if(now.before(newDate)) {
                return new ResponseEntity<Boolean>(true, HttpStatus.OK);
            } else {
                return new ResponseEntity<Boolean>(false, HttpStatus.OK);
            }
        } catch (Exception e){
            return new ResponseEntity<String>("Payment does not exist", HttpStatus.BAD_REQUEST);
        }
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
