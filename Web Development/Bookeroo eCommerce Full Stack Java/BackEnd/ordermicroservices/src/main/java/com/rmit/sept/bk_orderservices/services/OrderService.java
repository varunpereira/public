package com.rmit.sept.bk_orderservices.services;

import com.rmit.sept.bk_orderservices.exceptions.*;
import com.rmit.sept.bk_orderservices.model.*;
import com.rmit.sept.bk_orderservices.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;

    // inserts order record in the database
    public Order saveOrder (Order newOrder) {
        try {
            newOrder.setSellerusername(newOrder.getSellerusername());
            newOrder.setBuyerusername(newOrder.getBuyerusername());
            newOrder.setIsbn(newOrder.getIsbn());
            newOrder.setCurrentorder(newOrder.isCurrentorder());
            newOrder.setOrdergroup(newOrder.getOrdergroup());
            newOrder.setQuantity(newOrder.getQuantity());
            newOrder.setItemprice(newOrder.getItemprice());
            double totalItemPrice = newOrder.getQuantity() * newOrder.getItemprice();
            newOrder.setItemprice(totalItemPrice);
            newOrder.setNewbook(newOrder.isNewbook());
            newOrder.setLoanedbook(newOrder.isLoanedbook());
            return orderRepository.save(newOrder);

        } catch (Exception e){
            throw new OrderAlreadyExistsException("Order cannot be created");
        }
    }
    
    // updates order record in the database
    public List<Order> updateOrder(String buyerusername) {
        try {
            List<Order> updateOrder = orderRepository.findAllByBuyerusername(buyerusername);

            boolean isComplete = false;
            for(int i = 0; i < updateOrder.size(); i++) {
	            updateOrder.get(i).setCurrentorder(isComplete);
            }
            return (List<Order>) orderRepository.saveAll(updateOrder);
        } catch (Exception e){
            throw new OrderAlreadyExistsException("Cannot update order");
        }
    }

    // cancel current order
    public void cancelOrderItem(Long id) {
        try {
                Order order = orderRepository.findByOrderid(id);
                orderRepository.delete(order);
        } catch (Exception e){
            throw new OrderAlreadyExistsException("Cannot cancel order item");
        }
    }

    // returns the is Confirmed for an order
    // returns an list of orders or an empty [] (array)
    public List<Order> findIfCurrentOrder(boolean currentOrder,  String buyerusername){
        try {
            List<Order> orderStatus = orderRepository.findAllByCurrentorderAndBuyerusername(currentOrder, buyerusername);
            return orderStatus;
        } catch (Exception e){
            throw new OrderAlreadyExistsException("Orders not found");
        }
    }

    // finds all orders by username
    public List<Order> findOrders(String username){
        try{
        	boolean isCurrentOrder = true;
            List<Order> orders = orderRepository.findAllByCurrentorderAndBuyerusername(isCurrentOrder, username);
            return orders;
        } catch (Exception e){
            throw new OrderAlreadyExistsException("Orders with " + username +  " not found");
        }
    }
    
    // finds all orders by order group
    public List<Order> findOrdersByOrderGroup(Integer ordergroup){
        try{
            List<Order> orders = orderRepository.findAllByOrdergroup(ordergroup);
            return orders;
        } catch (Exception e){
            throw new OrderAlreadyExistsException("Orders not found");
        }
    }
    
    // finds past orders by username
    public List<Order> findPastOrders(String username){
        try{
        	boolean isCurrentOrder = false;
            List<Order> orders = orderRepository.findAllByCurrentorderAndBuyerusername(isCurrentOrder, username);
            return orders;
        } catch (Exception e){
            throw new OrderAlreadyExistsException("Orders with " + username +  " not found");
        }
    }
    
    // returns a list of users if they ordered something from the seller
    public List<Order> findSeller(String buyerusername,  String sellerUsername){
        try {
            List<Order> seller = orderRepository.findAllByBuyerusernameAndSellerusername(buyerusername, sellerUsername);
            return seller;
        } catch (Exception e){
            throw new OrderAlreadyExistsException("Orders with " + buyerusername + ", and " + sellerUsername + " not found");
        }
    }
	
	// find if a order exists for testing purposes
    public boolean orderExists(Long id){
        return orderRepository.existsByOrderid(id);
    }
    
    // Gets the order price
    public double getOrderPrice(String buyerusername, Integer orderGroup) {
        if (buyerusername != null && orderGroup != null) {
            return orderRepository.findByOrderPrice(buyerusername, orderGroup);
        }
        return 0.0;
    }
    
    // updates order price in the database
    public List<Order> updateOrderPrice(Integer group, String buyerusername, double orderPrice) {
        try {
            List<Order> updateOrderPrice = orderRepository.findAllByOrdergroupAndBuyerusername(group, buyerusername);
            for(int i = 0; i < updateOrderPrice.size(); i++) {
            	updateOrderPrice.get(i).setOrderprice(orderPrice);
            }
            return (List<Order>) orderRepository.saveAll(updateOrderPrice);
        } catch (Exception e){
            throw new OrderAlreadyExistsException("Cannot update order price");
        }
    }

}