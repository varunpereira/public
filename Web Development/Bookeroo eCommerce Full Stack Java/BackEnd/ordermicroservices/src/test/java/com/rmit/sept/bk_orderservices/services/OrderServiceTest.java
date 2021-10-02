package com.rmit.sept.bk_orderservices.services;

import com.rmit.sept.bk_orderservices.model.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import static org.junit.Assert.*;
import org.junit.jupiter.api.*;

import java.math.BigDecimal;
import java.util.List;



@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class OrderServiceTest {
	
	Order order;

    @Autowired
    OrderService orderService;
	
	@BeforeAll
   /* public void init(){
		// Order saved
        order = new Order();
        order.setSellerusername("Arthur A. Levine Books");
        order.setBuyerusername("bobroberts@gmail.com");
        order.setIsbn("9780545010221");
        order.setShippingaddress("3 Hill Road");
        order.setQuantity(1);
        order.setPrice(35.33);
        order.isCurrentorder();
        orderService.saveOrder(order);
        System.out.println("Order saved");
    }*/

    @Test
    void findOrderStatus() {
    	// Finds orders by status
        String username = "bobroberts@gmail.com";
        List<Order> orders = orderService.findIfCurrentOrder(true, username);
        Order orderOne = orders.get(0);
        assertEquals(username, orderOne.getBuyerusername());
        assertEquals(true, orderOne.isCurrentorder());
        System.out.println("Order confirmed found");
    }

    @Test
    void findOrders() {
    	 // Finds orders by username
        String username = "bobroberts@gmail.com";
        List<Order> orders = orderService.findOrders(username);
        Order orderOne = orders.get(0);
        assertEquals(username, orderOne.getBuyerusername());
        System.out.println("Order found");
    }
    
    @AfterAll
    void cancelOrder() {
        // Cancels Order
		orderService.cancelOrder(1L);
		assertEquals(false, orderService.orderExists(1L));
        System.out.println("Order canceled");
		
    }
}