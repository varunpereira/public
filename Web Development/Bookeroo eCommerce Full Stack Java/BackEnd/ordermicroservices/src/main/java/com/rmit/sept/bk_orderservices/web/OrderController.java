package com.rmit.sept.bk_orderservices.web;

import com.rmit.sept.bk_orderservices.model.*;
import com.rmit.sept.bk_orderservices.repositories.OrderRepository;
import com.rmit.sept.bk_orderservices.services.*;

import org.apache.commons.lang3.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Date;
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
     
     List<Order> updateOrder = orderService.updateOrder(username);

     return new ResponseEntity<Boolean>(true, HttpStatus.CREATED);
    }

    // User interaction
    
    // cancels the order in the database
    @DeleteMapping("/cancelOrder/{id}")
    public ResponseEntity<String> cancelOrderByID (@PathVariable(value = "id") Long id) {
        try {
            Order order = orderRepository.findByOrderid(id);
            Date orderDateTime = order.getDatetime();
            Date newDate = DateUtils.addHours(orderDateTime, 2);
            Date now = new Date();
            // checks if order has been canceled after two hours
            if(now.before(newDate)) {
                orderService.cancelOrder(id);
                return new ResponseEntity<String>("Order with id: " + id  + " deleted", HttpStatus.OK);
            } else {
                return new ResponseEntity<String>("Order cannot be canceled after two hours after the item is ordered", HttpStatus.OK);
            }
        } catch (Exception e){
            return new ResponseEntity<String>("Order with: " + id + " does not exist", HttpStatus.BAD_REQUEST);
        }
    }
    
    // Order history
    
    // finds order's by username
    @PostMapping("/findOrders/{username}")
    public List<Order> findOrdersByUser(@PathVariable(value = "username") String username){
        return orderService.findOrders(username);
    }
    
    // Review for user interaction
    
    // finds order's by seller for a user
    @PostMapping("/findSeller/{buyer}/{seller}")
    public List<Order> findOrderByBuyerSeller(@PathVariable(value = "buyer") String  buyer,
                                         @PathVariable(value = "seller") String seller) {
        return orderService.findSeller(buyer, seller);
    }
    
    
}
