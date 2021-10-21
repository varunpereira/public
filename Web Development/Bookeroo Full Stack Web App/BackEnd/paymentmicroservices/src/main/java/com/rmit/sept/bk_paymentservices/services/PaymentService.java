package com.rmit.sept.bk_paymentservices.services;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.rmit.sept.bk_paymentservices.exceptions.PaymentAlreadyExistsException;
import com.rmit.sept.bk_paymentservices.repository.PaymentRepository;
import com.paypal.api.payments.Amount;
import com.paypal.api.payments.Payer;
import com.paypal.api.payments.Payment;
import com.paypal.api.payments.PaymentExecution;
import com.paypal.api.payments.RedirectUrls;
import com.paypal.api.payments.RefundRequest;
import com.paypal.api.payments.Sale;
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
            throw new PaymentAlreadyExistsException("Payment cannot be created");
        }
     }
	
	 // find transaction by buyer username
    public List<com.rmit.sept.bk_paymentservices.model.Payment> findTransactions(String username){
        try{
            List<com.rmit.sept.bk_paymentservices.model.Payment> payment = paymentRepository.findAllByBuyerusername(username);
            return payment;
        } catch(Exception e){
            throw new PaymentAlreadyExistsException("Transactions with username not found");
        }
    }
    
    // find if a payment exists
    public boolean paymentExists(Long id){
        return paymentRepository.existsByPaymentid(id);
    }
    
    // After Refund
    
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
    
    // Before Refund
    
    // update sale id
    public void updateSaleID(String saleId, Long paymentid) {
    	try {
    		com.rmit.sept.bk_paymentservices.model.Payment payment = paymentRepository.findByPaymentid(paymentid);
    		payment.setSaleid(saleId);
    		paymentRepository.save(payment);
    	} catch (Exception e) {
    		 throw new PaymentAlreadyExistsException("Sale ID cannot be updated");
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
	
	// refunds a sale
	public void refundSale(String saleID, String totalAmount) {
		
				Sale sale = new Sale();
				sale.setId(saleID);
				
				RefundRequest refund = new RefundRequest();
				
				Amount amount = new Amount();
				amount.setCurrency("AUD");
				amount.setTotal(totalAmount);
				refund.setAmount(amount);
				
				try {	
					sale.refund(apiContext, refund);
				} catch (PayPalRESTException e) {
					System.out.println(e.getMessage());
				}
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


	// find transaction by order group
	public com.rmit.sept.bk_paymentservices.model.Payment findTransactionbyOrdergroup(Integer ordergroup){
		try{
			com.rmit.sept.bk_paymentservices.model.Payment payment = paymentRepository.findByOrdergroup(ordergroup);
			return payment;
		} catch(Exception e){
			throw new PaymentAlreadyExistsException("Transactions with order group not found");
		}
	}

	// refund past purchases
    public com.rmit.sept.bk_paymentservices.model.Payment refundWithinTime(Long id) {
        try {
        		com.rmit.sept.bk_paymentservices.model.Payment payment = paymentRepository.findByPaymentid(id);
        		return payment;
        } catch (Exception e){
            throw new PaymentAlreadyExistsException("Cannot refund purchase");
        }
    }

}
