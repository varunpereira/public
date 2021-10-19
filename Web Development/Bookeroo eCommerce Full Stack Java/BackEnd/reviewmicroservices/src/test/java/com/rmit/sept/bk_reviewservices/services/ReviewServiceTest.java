package com.rmit.sept.bk_reviewservices.services;

import com.rmit.sept.bk_reviewservices.model.Review;
import static org.junit.Assert.*;

import java.util.List;

import org.junit.jupiter.api.*;
import org.junit.jupiter.api.MethodOrderer.Alphanumeric;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;


@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestMethodOrder(Alphanumeric.class)
class ReviewServiceTest {

	Review review;
	
    @Autowired
    ReviewService reviewService;
	
	@BeforeAll
    public void init(){
		// Review saved
        review = new Review();
        review.setUser("Bob");
        review.setDescription("Great book");
        review.setRating(5);
        review.setBookname("Harry Potter and the Deathly Hallows");
        reviewService.saveReview(review);
        System.out.println("Review saved");
    }

    @Test
    void AupdateReview() {
        // Update review
        review.setUser("Bob");
        review.setDescription("Not a great book");
        review.setRating(4);
        review.setBookname("Harry Potter and the Deathly Hallows");
        reviewService.updateReview(review, "Harry Potter and the Deathly Hallows");
		assertEquals(Integer.valueOf(4), review.getRating());
        System.out.println("Review updated");
    }

    @Test
    void BfindBookReviews() {
    	 // Finds review by book name 
        String bookName = "Harry Potter and the Deathly Hallows";
        List<Review> review = reviewService.findBookReviews(bookName);
        Review reviewOne = review.get(0);
        assertEquals(bookName, reviewOne.getBookname());
        System.out.println("Book found");
    }
    
    @AfterAll
    void deleteReview() {
        // Deletes Review
		reviewService.deleteReview(1L);
		assertEquals(false, reviewService.reviewExists(1L));
        System.out.println("Review deleted");
    }
}