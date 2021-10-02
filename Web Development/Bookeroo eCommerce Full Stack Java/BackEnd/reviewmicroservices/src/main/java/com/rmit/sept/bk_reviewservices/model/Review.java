package com.rmit.sept.bk_reviewservices.model;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.util.Date;

@Entity
public class Review {
    // Primary Key
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long reviewid;
    // Field passed by the front end
    // User who made review
    @NotBlank(message = "User cannot be blank")
    private String user;
    // Review Book
    @NotBlank(message = "Book name cannot be blank")
    private String bookname;
    // Review Description
    @Size(min = 1, max = 300, message = "Please enter 1 to 300 characters")
    @NotBlank(message = "Please enter a description")
    private String description;
    // Review Rating
    @Min(value = 1, message = "Rating must be between 1 and 5")
    @Max(value = 5, message = "Rating must be between 1 and 5")
    @NotNull(message = "Please enter a rating between 1 and 5")
    private Integer rating;
    // Review Timestamp
    private Date datetime;

    // Getters and setters

	public String getUser() {
		return user;
	}

	public Long getReviewid() {
		return reviewid;
	}

	public void setReviewid(Long reviewid) {
		this.reviewid = reviewid;
	}

	public void setUser(String user) {
		this.user = user;
	}

	public String getBookname() {
		return bookname;
	}

	public void setBookname(String bookname) {
		this.bookname = bookname;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Integer getRating() {
		return rating;
	}

	public void setRating(Integer rating) {
		this.rating = rating;
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
