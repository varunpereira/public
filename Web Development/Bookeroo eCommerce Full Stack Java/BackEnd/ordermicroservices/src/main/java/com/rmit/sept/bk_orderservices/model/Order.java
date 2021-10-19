package com.rmit.sept.bk_orderservices.model;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.util.Date;

@Entity
@Table(name = "orders")
public class Order {
    // Primary Key
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long orderid;
    // Book sellers username
    @Size(min = 1, max = 100, message = "Please enter 1 to 100 characters")
    @NotBlank(message = "Please enter a seller name")
    private String sellerusername;
    // Book buyers username
    @Size(min = 1, max = 100, message = "Please enter 1 to 100 characters")
    @NotBlank(message = "Please enter a buyer name")
    private String buyerusername;
    // Books isbn
    @NotBlank(message = "Please enter an ISBN")
    private String isbn;
    // Book quantity
    @Min(value = 1, message = "Must have a minimum quantity of 1 book")
    @NotNull
    private double quantity;
    // Book order status
    // Once order created in the cart it will be true, once purchased it will be false
    @NotNull
    private boolean currentorder = true;
    // Order Group
    @NotNull
    private Integer ordergroup;
    // Item price
    @NotNull(message = "Please enter an item price")
    private double itemprice;
    // Order price
    @NotNull(message = "Please enter an order price")
    private double orderprice = 0;
    // Books type - used or new book
    @NotNull(message = "Please enter a book type")
    private boolean newbook;
    // Book availability - buy or loan 
    @NotNull(message = "Please enter a books availability")
    private boolean loanedbook;
    // Timestamp of order
    private Date datetime;

    // Getters and setters

	public Long getOrderid() {
		return orderid;
	}

	public void setOrderid(Long orderid) {
		this.orderid = orderid;
	}

	public String getSellerusername() {
		return sellerusername;
	}

	public void setSellerusername(String sellerusername) {
		this.sellerusername = sellerusername;
	}

	public String getBuyerusername() {
		return buyerusername;
	}

	public void setBuyerusername(String buyerusername) {
		this.buyerusername = buyerusername;
	}

	public String getIsbn() {
		return isbn;
	}

	public void setIsbn(String isbn) {
		this.isbn = isbn;
	}

	public double getQuantity() {
		return quantity;
	}

	public void setQuantity(double quantity) {
		this.quantity = quantity;
	}

	public boolean isCurrentorder() {
		return currentorder;
	}

	public void setCurrentorder(boolean currentorder) {
		this.currentorder = currentorder;
	}

	public Integer getOrdergroup() {
		return ordergroup;
	}

	public void setOrdergroup(Integer ordergroup) {
		this.ordergroup = ordergroup;
	}

	public double getItemprice() {
		return itemprice;
	}

	public void setItemprice(double itemprice) {
		this.itemprice = itemprice;
	}

	public double getOrderprice() {
		return orderprice;
	}

	public void setOrderprice(double orderprice) {
		this.orderprice = orderprice;
	}
	
	public boolean isNewbook() {
		return newbook;
	}

	public void setNewbook(boolean newbook) {
		this.newbook = newbook;
	}
	
	public boolean isLoanedbook() {
		return loanedbook;
	}

	public void setLoanedbook(boolean loanedbook) {
		this.loanedbook = loanedbook;
	}

	public Date getDatetime() {
		return datetime;
	}

	public void setDatetime(Date datetime) {
		this.datetime = datetime;
	}
	
	 @PrePersist
	 protected void onCreate() {
	    this.datetime = new Date();
	 }
}
