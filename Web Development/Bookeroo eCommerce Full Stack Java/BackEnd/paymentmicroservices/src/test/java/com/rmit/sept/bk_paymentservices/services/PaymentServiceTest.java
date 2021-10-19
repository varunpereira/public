package com.rmit.sept.bk_paymentservices.services;

import static org.junit.jupiter.api.Assertions.*;
import java.util.List;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.MethodOrderer.Alphanumeric;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.rmit.sept.bk_paymentservices.model.Payment;

@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestMethodOrder(Alphanumeric.class)
class PaymentServiceTest {
	
	Payment payment;
	
	@Autowired
	PaymentService paymentService;

	@BeforeAll
	public void init() {
		// Payment saved
		payment = new Payment();
		payment.setBuyerusername("bobroberts@gmail.com");
		payment.setPrice(50);
		payment.setOrdergroup(1);
		payment.setShippingaddress("1 ABC Street");
		paymentService.savePayment(payment);
		System.out.println("Payment saved");
	}	

	@Test
	void AfindTransactions() {
		// Find transactions by username
		String username = "bobroberts@gmail.com"; 
		List<Payment> payment = paymentService.findTransactions(username);
		Payment paymentOne = payment.get(0);
		assertEquals(username, paymentOne.getBuyerusername());
		System.out.println("Transaction found by username");
	}

	@Test
	void BpaymentExists() {
		// Finds if a payment exists
		boolean exists = paymentService.paymentExists(1L);
		assertEquals(true, exists);
	}
	
	@Test
	void CtransactionHistory() {
		// Finds transactions by datetime ascending
		String username = "bobroberts@gmail.com"; 
		List<Payment> payment = paymentService.transactionHistory();
		Payment paymentOne = payment.get(0);
		assertEquals(username, paymentOne.getBuyerusername());
		System.out.println("Transactions found");
	}
	
	@Test
	void DfindTransactionbyOrdergroup() {
		// Find transactions by order group
		Integer orderGroup = 1;
		Payment payment = paymentService.findTransactionbyOrdergroup(orderGroup);
		assertEquals(orderGroup, payment.getOrdergroup());
		System.out.println("Transactions found by ordergroup");
	}
}
