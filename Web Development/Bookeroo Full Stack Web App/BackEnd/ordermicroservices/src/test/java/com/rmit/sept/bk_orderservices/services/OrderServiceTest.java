package com.rmit.sept.bk_orderservices.services;

import com.rmit.sept.bk_orderservices.model.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import static org.junit.Assert.*;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.MethodOrderer.Alphanumeric;
import java.util.List;

@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestMethodOrder(Alphanumeric.class)
class OrderServiceTest {
	
	Order order;

    @Autowired
    OrderService orderService;
	
	@BeforeAll
    public void init(){
		// Order saved
        order = new Order();
        order.setSellerusername("Arthur A. Levine Books");
        order.setBuyerusername("bobroberts@gmail.com");
        order.setIsbn("9780545010221");
        order.isCurrentorder();
        order.setOrdergroup(1);
        order.setQuantity(5);
        order.setItemprice(30);
        order.setNewbook(false);
        order.setLoanedbook(false);
        orderService.saveOrder(order);
        System.out.println("Order saved");
    }

	 @Test
	 void AfindIfCurrentOrder() {
		 // Finds orders by status
	     String username = "bobroberts@gmail.com";
	     List<Order> orders = orderService.findIfCurrentOrder(true, username);
	     Order orderOne = orders.get(0);
	     assertEquals(username, orderOne.getBuyerusername());
	     assertEquals(true, orderOne.isCurrentorder());
	     System.out.println("Current order found");
	}  
	
	@Test
    void BfindOrders() {
    	 // Finds orders by username
        String username = "bobroberts@gmail.com";
        List<Order> orders = orderService.findOrders(username);
        Order orderOne = orders.get(0);
        assertEquals(username, orderOne.getBuyerusername());
        System.out.println("Current orders found");
    }
	
	@Test
    void CfindOrdersByOrderGroup() {
    	 // Finds orders by group
        Integer orderGroup = 1;
        List<Order> orders = orderService.findOrdersByOrderGroup(1);
        Order orderOne = orders.get(0);
        assertEquals(orderGroup, orderOne.getOrdergroup());
        System.out.println("Orders by order group found");
    }
	
	@Test
	void DupdateOrder() {
		// Updates the current order to false
		String username = "bobroberts@gmail.com";
		List<Order> update = orderService.updateOrder(username);
		assertEquals(false, update.get(0).isCurrentorder());
	    System.out.println("Order updated");
	}
	
	@Test
	void EfindPastOrders() {
		// Finds past orders
		String username = "bobroberts@gmail.com";
		List<Order> orders = orderService.findPastOrders(username);
		Order orderOne = orders.get(0);
		assertEquals(username, orderOne.getBuyerusername());
		System.out.println("Past orders found");
	}
	
	@Test
	void FfindSeller() {
		// Finds the seller given the username
		String username = "bobroberts@gmail.com";
		String sellername = "Arthur A. Levine Books";
		List<Order> orders = orderService.findSeller(username, sellername);
		Order orderOne = orders.get(0);
		assertEquals(username, orderOne.getBuyerusername());
		assertEquals(sellername, orderOne.getSellerusername());
		System.out.println("Seller found for given username");
	}
	
	@Test
	void GgetOrderPrice() {
		// Gets the order price for the given order group
        double orderPrice = orderService.getOrderPrice("bobroberts@gmail.com", order.getOrdergroup());
        assertEquals(150, orderPrice, 0);
        System.out.println("Order price is calculated");
	}
	
	@Test
	void HupdateOrderPrice() {
		// Updates the order price for the given order group
		double itemPrice = 150;
		List<Order> updatePrice = orderService.updateOrderPrice(order.getOrdergroup(), order.getBuyerusername(), itemPrice);
		Order orderOne = updatePrice.get(0);
		assertEquals(150, orderOne.getOrderprice(), 0);
		System.out.println("Order price is updated");
	}
	
    @AfterAll
    void cancelOrderItem() {
	   // Cancels or refunds an order within a time frame
	   orderService.cancelOrderItem(1L);
	   assertEquals(false, orderService.orderExists(1L));
	   System.out.println("Order canceled");	
    }
}