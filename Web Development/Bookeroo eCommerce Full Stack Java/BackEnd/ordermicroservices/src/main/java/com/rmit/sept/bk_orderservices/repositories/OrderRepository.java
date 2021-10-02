package com.rmit.sept.bk_orderservices.repositories;

import com.rmit.sept.bk_orderservices.model.*;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends CrudRepository<Order, Long> {
    // Delete book
    void delete(Order order);
    // Finds order by ID
    Order findByOrderid(Long id);
    // Finds orders by username
    List<Order> findAllByOrdergroupAndBuyerusername(Integer group, String buyerusername);
    // Finds order's by status for given username
    List<Order> findAllByCurrentorderAndBuyerusername(boolean currentOrder, String buyerusername);
	// Find orders by username
    List<Order> findAllByBuyerusername(String buyerusername);
    // Finds order's by buyer and seller username
    List<Order> findAllByBuyerusernameAndSellerusername(String buyerusername, String sellerUsername);
	// Exists by ID
	boolean existsByOrderid(Long id);
	// Sum order price
	@Query(value = "SELECT SUM(itemprice) FROM Orders WHERE buyerusername =:buyerusername AND ordergroup =:ordergroup", nativeQuery = true)
	double findByOrderPrice(@Param("buyerusername") String buyerusername, @Param("ordergroup") Integer ordergroup); 
	// Finds the book by isbn and book type
    //Order findByIsbnAndBooktype(String isbn, String booktype);
    // Check book lone
    Order findByIsbn(String isbn);
}
