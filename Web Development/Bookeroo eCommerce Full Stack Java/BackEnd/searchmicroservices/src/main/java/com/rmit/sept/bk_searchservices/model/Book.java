package com.rmit.sept.bk_searchservices.model;

import org.hibernate.validator.constraints.URL;

import javax.persistence.*;
import javax.validation.constraints.*;

@Entity
public class Book {
    // Primary Key
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long bookid;
    // Book name
    @Size(min = 1, max = 100, message = "Please enter 1 to 100 characters")
    @NotBlank(message = "Please enter a book name")
    private String name;
    // Book author
    @Size(min = 1, max = 100, message = "Please enter 1 to 100 characters")
    @NotBlank(message = "Please enter an author name")
    private String author;
    // Books unique ISBN
    @NotBlank(message = "Please enter an ISBN")
    @Column(unique = true)
    private String isbn;
    // Book category
    @Size(min = 1, max = 100, message = "Please enter 1 to 100 characters")
    @NotBlank(message = "Please enter a category")
    private String category;
    // Book price
    @NotNull(message = "Please enter a price")
    private double price;
    // Book seller
    @Size(min = 1, max = 100, message = "Please enter 1 to 100 characters")
    @NotBlank(message = "Please enter a seller")
    private String seller;
    // Books stock level
    @NotNull(message = "Please enter a stock level")
    private Integer stocklevel;
    // Books type - used or new book
    @NotNull(message = "Please enter a book type")
    private boolean newbook;
    // Book availability - buy or loan 
    @NotNull(message = "Please enter a books availability")
    private boolean loanedbook;
    // Book cover image URL
    @NotBlank(message = "Please enter a cover image URL")
    @URL
    private String coverurl;
    // Book table of contents URL
    @URL
    @NotBlank(message = "Please enter a table of contents URL")
    private String contenturl;
    
    // Getters and setters
    
	public Long getBookid() {
		return bookid;
	}
	
	public void setBookid(Long bookid) {
		this.bookid = bookid;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getAuthor() {
		return author;
	}
	
	public void setAuthor(String author) {
		this.author = author;
	}
	
	public String getIsbn() {
		return isbn;
	}
	
	public void setIsbn(String isbn) {
		this.isbn = isbn;
	}
	
	public String getCategory() {
		return category;
	}
	
	public void setCategory(String category) {
		this.category = category;
	}
	
	public double getPrice() {
		return price;
	}
	
	public void setPrice(double price) {
		this.price = price;
	}
	
	public String getSeller() {
		return seller;
	}
	
	public void setSeller(String seller) {
		this.seller = seller;
	}
	
	public Integer getStocklevel() {
		return stocklevel;
	}
	
	public void setStocklevel(Integer stocklevel) {
		this.stocklevel = stocklevel;
	}
	
	public boolean isNewbook() {
		return newbook;
	}

	public void setNewbook(boolean newbook) {
		this.newbook = newbook;
	}

	public boolean isLoanedBook() {
		return loanedbook;
	}

	public void setLoanedBook(boolean loanedBook) {
		this.loanedbook = loanedBook;
	}

	public String getCoverurl() {
		return coverurl;
	}
	
	public void setCoverurl(String coverurl) {
		this.coverurl = coverurl;
	}
	
	public String getContenturl() {
		return contenturl;
	}
	
	public void setContenturl(String contenturl) {
		this.contenturl = contenturl;
	}
}
