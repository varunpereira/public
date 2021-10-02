package com.rmit.sept.bk_paymentservices.services;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rmit.sept.bk_paymentservices.exceptions.PaymentAlreadyExistsException;
import com.rmit.sept.bk_paymentservices.model.*;
import com.rmit.sept.bk_paymentservices.repository.PaymentRepository;
import com.paypal.api.payments.Amount;
import com.paypal.api.payments.Payer;
import com.paypal.api.payments.Payment;
import com.paypal.api.payments.PaymentExecution;
import com.paypal.api.payments.RedirectUrls;
import com.paypal.api.payments.Transaction;
import com.paypal.base.rest.APIContext;
import com.paypal.base.rest.PayPalRESTException;

import java.io.IOException;
import java.io.Writer;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

@Service
public class PaymentService {
	
	@Autowired
	private APIContext apiContext; 
	
	@Autowired
	private PaymentRepository paymentRepository;
	
	// inserts payment record into database
	public com.rmit.sept.bk_paymentservices.model.Payment savePayment (com.rmit.sept.bk_paymentservices.model.Payment newPayment) {
        try {
        	newPayment.setBuyerusername(newPayment.getBuyerusername());
			newPayment.setPrice(newPayment.getPrice());
			newPayment.setCurrency(newPayment.getCurrency());
			newPayment.setMethod(newPayment.getMethod());
			newPayment.setIntent(newPayment.getIntent());
			newPayment.setDescription(newPayment.getDescription());
			newPayment.setOrdergroup(newPayment.getOrdergroup());
			newPayment.setShippingaddress(newPayment.getShippingaddress());
        	return paymentRepository.save(newPayment);
        } catch (Exception e){
            throw new PaymentAlreadyExistsException("Payment with: '" + newPayment.getPaymentid() + "', already exists");
        }
     }
	
	 // find transaction by book
    public List<com.rmit.sept.bk_paymentservices.model.Payment> findTransactions(String username){
        try{
            List<com.rmit.sept.bk_paymentservices.model.Payment> payment = paymentRepository.findAllByBuyerusername(username);
            return payment;
        } catch(Exception e){
            throw new PaymentAlreadyExistsException("Transactions not found");
        }
    }
    
    // find if a payment exists for testing purposes
    public boolean paymentExists(Long id){
        return paymentRepository.existsByPaymentid(id);
    }
    
    // refunds a payment by order group
    public void paymentRefund(Integer group) {
    	try {
    		com.rmit.sept.bk_paymentservices.model.Payment payment = paymentRepository.findByOrdergroup(group);
    		payment.setRefund(true);
    		paymentRepository.save(payment);
    	} catch (Exception e) {
    		 throw new PaymentAlreadyExistsException("Payment cannot be refunded");
    	}	
    }
    
    // lists all transaction history
    public List<com.rmit.sept.bk_paymentservices.model.Payment> transactionHistory(){
    	return paymentRepository.findAllByOrderByDatetimeAsc();
    }
	
	// creates paypal payment
	public Payment createPayment(
			Double total, 
			String currency, 
			String method,
			String intent,
			String description, 
			String cancelUrl, 
			String successUrl) throws PayPalRESTException {
			
			Amount amount = new Amount();
			amount.setCurrency(currency);
			total = new BigDecimal(total).setScale(2, RoundingMode.HALF_UP).doubleValue();
			amount.setTotal(String.format("%.2f", total));

			Transaction transaction = new Transaction();
			transaction.setDescription(description);
			transaction.setAmount(amount);

			List<Transaction> transactions = new ArrayList<>();
			transactions.add(transaction);

			Payer payer = new Payer();
			payer.setPaymentMethod(method.toString());

			Payment payment = new Payment();
			payment.setIntent(intent.toString());
			payment.setPayer(payer);  
			payment.setTransactions(transactions);
			RedirectUrls redirectUrls = new RedirectUrls();
			redirectUrls.setCancelUrl(cancelUrl);
			redirectUrls.setReturnUrl(successUrl);
			payment.setRedirectUrls(redirectUrls);

			return payment.create(apiContext);
	}
	
	// performs paypal payment
	public Payment executePayment(String paymentId, String payerId) throws PayPalRESTException{
		Payment payment = new Payment();
		payment.setId(paymentId);
		PaymentExecution paymentExecute = new PaymentExecution();
		paymentExecute.setPayerId(payerId);
		return payment.execute(apiContext, paymentExecute);
	}
	
	// Transaction reports
	
	// Basic transaction info
	   public void quickTransactionListCsv(Writer writer) {
	    	List<com.rmit.sept.bk_paymentservices.model.Payment> payments = paymentRepository.findAll();
	    	try (CSVPrinter csvPrinter = new CSVPrinter(writer, CSVFormat.DEFAULT)){
	    			for (com.rmit.sept.bk_paymentservices.model.Payment payment: payments) {
	    				csvPrinter.printRecord(payment.getBuyerusername(), payment.getOrdergroup(), "Refunded: " + payment.isRefund(), payment.getDatetime());
	    			}
	    		} catch (IOException e) {
	    			throw new PaymentAlreadyExistsException("CSV cannot be generated");
	    		}
	    	}
	   
	   // Detailed transaction info
	   public void detailedTransactionListCsv(Writer writer) {
	    	List<com.rmit.sept.bk_paymentservices.model.Payment> payments = paymentRepository.findAll();
	    	try (CSVPrinter csvPrinter = new CSVPrinter(writer, CSVFormat.DEFAULT)){
	    			for (com.rmit.sept.bk_paymentservices.model.Payment payment: payments) {
	    				csvPrinter.printRecord(payment.getPaymentid(), payment.getBuyerusername(), payment.getOrdergroup(), payment.getPrice(), payment.getShippingaddress(), payment.getCurrency(), payment.getMethod(), payment.getIntent(), payment.getDescription(), payment.getDatetime());
	    			}
	    		} catch (IOException e) {
	    			throw new PaymentAlreadyExistsException("CSV cannot be generated");
	    		}
	    	}
	
}
