package com.rmit.sept.bk_paymentservices.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import com.rmit.sept.bk_paymentservices.model.Payment;

@Repository	
public interface PaymentRepository extends CrudRepository<Payment, Long> {
	// Find payment by username
	List<Payment> findAllByBuyerusername(String username);
	// Exists by payment ID
	boolean existsByPaymentid(Long id);
	// Finds a payment by ID
	Payment findByPaymentid(Long id);
	// Find by order group
	Payment findByOrdergroup(Integer group);
	// Find all payments
	List<Payment> findAll();
	// Find all payments order by date
	List<Payment> findAllByOrderByDatetimeAsc();
}
