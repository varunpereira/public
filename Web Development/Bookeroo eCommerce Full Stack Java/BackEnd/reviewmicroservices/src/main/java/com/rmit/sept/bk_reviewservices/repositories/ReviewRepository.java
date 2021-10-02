package com.rmit.sept.bk_reviewservices.repositories;

import com.rmit.sept.bk_reviewservices.model.Review;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReviewRepository extends CrudRepository<Review, Long> {
    // Delete review
    void delete(Review review);
    // Find review by ID
    Review findByReviewid(Long id);
    // Finds a review by book name
    Review findReviewByBookname(String bookName);
    // Finds all reviews by book name
    List<Review> findAllByBookname(String bookName);
	// Exists by Review ID
	boolean existsByReviewid(Long id);
}
