package com.rmit.sept.bk_paymentservices.model;

import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.PrePersist;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
public class Payment {
	// Primary key
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long paymentid;
    // Book buyers username
    @Size(min = 1, max = 100, message = "Please enter 1 to 100 characters")
    @NotBlank(message = "Please enter a buyer name")
    private String buyerusername;
    //  Users Shipping Address
    @NotBlank(message = "Please enter a shipping address")
    private String shippingaddress;
    // Order Group
    @NotNull
 	private Integer ordergroup;
    // Timestamp of order
    private Date datetime;
    // Refund status
    private boolean refund = false;
	// For total paypal payment
	private double price;
	private String currency = "AUD";
	private String method = "paypal";
	private String intent = "sale";
	private String description = "paypal payment";
	
	private String saleid;
	
    // Getters and setters
	
	public Long getPaymentid() {
		return paymentid;
	}

	public void setPaymentid(Long paymentid) {
		this.paymentid = paymentid;
	}

	public String getBuyerusername() {
		return buyerusername;
	}

	public void setBuyerusername(String buyerusername) {
		this.buyerusername = buyerusername;
	}

	public Integer getOrdergroup() {
		return ordergroup;
	}

	public void setOrdergroup(Integer ordergroup) {
		this.ordergroup = ordergroup;
	}

	public Date getDatetime() {
		return datetime;
	}

	public void setDatetime(Date datetime) {
		this.datetime = datetime;
	}
	
	public boolean isRefund() {
		return refund;
	}

	public void setRefund(boolean refund) {
		this.refund = refund;
	}
	
	public String getShippingaddress() {
		return shippingaddress;
	}

	public void setShippingaddress(String shippingaddress) {
		this.shippingaddress = shippingaddress;
	}
	
	public String getSaleid() {
		return saleid;
	}

	public void setSaleid(String saleid) {
		this.saleid = saleid;
	}
	
	// Paypal Getters and setters

	public void setPrice(double price){
		this.price = price;
	}
	
	public double getPrice(){
		return price;
	}
	
	public void setCurrency(String currency){
		this.currency = currency;
	}
	
	public String getCurrency(){
		return currency;
	}
	
	public void setMethod(String method){
		this.method = method;
	}
	
	public String getMethod(){
		return method;
	}
	
	public void setIntent(String intent){
		this.intent = intent;
	}
	
	public String getIntent(){
		return intent;
	}
	
	public void setDescription(String description){
		this.description = description;
	}
	
	public String getDescription(){
		return description;
	}

	// End Paypal Getters and setters

	@PrePersist
    protected void onCreate() {
        this.datetime = new Date();
    }
}
