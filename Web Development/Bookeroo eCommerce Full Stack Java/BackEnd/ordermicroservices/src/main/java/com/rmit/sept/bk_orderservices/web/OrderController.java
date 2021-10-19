package com.rmit.sept.bk_orderservices.web;

import com.rmit.sept.bk_orderservices.model.*;
import com.rmit.sept.bk_orderservices.repositories.OrderRepository;
import com.rmit.sept.bk_orderservices.services.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/api/order")
public class OrderController {

    @Autowired
    private MapValidationErrorService mapValidationErrorService;

    @Autowired
    private OrderService orderService;

    @Autowired
    private OrderRepository orderRepository;
    
    // Adding an order
    
    // finds order's by status for a user
    @PostMapping("/isOrderCurrent/{username}")
    public ResponseEntity<?> findOrderIfConfirmed(@PathVariable(value = "username") String username) {
    	boolean isConfirmed = true;
    	List<Order> currentOrderList = orderService.findIfCurrentOrder(isConfirmed, username);
    	if (currentOrderList.size() > 0) {
    		return new ResponseEntity<Integer>(currentOrderList.get(0).getOrdergroup(), HttpStatus.CREATED);			 
    	} else {
    		return new ResponseEntity<Boolean>(false, HttpStatus.CREATED);
    	}
    }

    // adds the order to the database
    @PostMapping("/addOrder")
    public ResponseEntity<?> addOrder(@Valid @RequestBody Order order, BindingResult result){
        ResponseEntity<?> errorMap = mapValidationErrorService.MapValidationService(result);
        if (errorMap != null) {
            return errorMap;
        }

        Order newOrder = orderService.saveOrder(order);

        return new ResponseEntity<Order>(newOrder, HttpStatus.CREATED);
    }
    
    // Before Paypal Purchase
    
    //  Gets the order price
    @PostMapping("/getOrderPrice/{username}/{group}")
    public double getSumOrder(@PathVariable(value = "username") String username,
    						  @PathVariable(value = "group") Integer group) {
        return orderService.getOrderPrice(username, group);
    }
    
    // updates order price after purchases to true
    @PatchMapping("/updateOrderPrice/{group}/{username}/{orderprice}")
    public ResponseEntity<?> updateTotalOrderPrice(@PathVariable(value = "group") Integer group,
    										   @PathVariable(value = "username") String username,
    										   @PathVariable(value = "orderprice") double orderprice){
     
        orderService.updateOrderPrice(group, username, orderprice);

        return new ResponseEntity<Boolean>(true, HttpStatus.CREATED);
    }
    
    // After Paypal Purchase
    
    // update confirmed status to false
    @PatchMapping("/updateConfirmStatus/{username}")
    public ResponseEntity<?> updateBookDetails(@PathVariable(value = "username") String username){
     
     orderService.updateOrder(username);

     return new ResponseEntity<Boolean>(true, HttpStatus.CREATED);
    }

    // User interaction
    
    //  cancel current order
    @DeleteMapping("/cancelOrderItem/{id}")
    public ResponseEntity<?> cancelOrder (@PathVariable(value = "id") Long id) {
        	orderService.cancelOrderItem(id);
            return new ResponseEntity<Boolean>(true, HttpStatus.OK);
    }
    
    // Order history
    
    // finds order's by username
    @PostMapping("/findOrders/{username}")
    public List<Order> findOrdersByUser(@PathVariable(value = "username") String username){
        return orderService.findOrders(username);
    }
    
   // finds order's by ordergroup
    @PostMapping("/findOrdersByGroup/{ordergroup}")
    public List<Order> findOrdersByGroup(@PathVariable(value = "ordergroup") Integer ordergroup){
        return orderService.findOrdersByOrderGroup(ordergroup);
    }

    @PostMapping("/findPastOrders/{username}")
    public List<Order> findPastOrdersByUser(@PathVariable(value = "username") String username){
        return orderService.findPastOrders(username);
    }
    
    // Review for user interaction
    
    // finds order's by seller for a user
    @PostMapping("/findSeller/{buyer}/{seller}")
    public List<Order> findOrderByBuyerSeller(@PathVariable(value = "buyer") String  buyer,
                                         @PathVariable(value = "seller") String seller) {
        return orderService.findSeller(buyer, seller);
    }
    
    
}
