package com.rmit.sept.bk_reviewservices.services;

import com.rmit.sept.bk_reviewservices.exceptions.ReviewAlreadyExistsException;
import com.rmit.sept.bk_reviewservices.model.Review;
import com.rmit.sept.bk_reviewservices.repositories.ReviewRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReviewService {

    @Autowired
    private ReviewRepository reviewRepository;

    // inserts review record into database
    public Review saveReview (Review newReview) {
        try {
            newReview.setUser(newReview.getUser());
            newReview.setDescription(newReview.getDescription());
            newReview.setRating(newReview.getRating());
            newReview.setBookname(newReview.getBookname());;
            return reviewRepository.save(newReview);

        } catch (Exception e){
            throw new ReviewAlreadyExistsException("Review cannot be created");
        }
    }

    // updates the review record in the database
    public Review updateReview (Review reviewDetails, String bookName){
        try{
            Review updateReview = reviewRepository.findReviewByBookname(bookName);
            updateReview.setUser(reviewDetails.getUser());
            updateReview.setDescription(reviewDetails.getDescription());
            updateReview.setRating(reviewDetails.getRating());
            updateReview.setBookname(reviewDetails.getBookname());
            return reviewRepository.save(updateReview);

        } catch (Exception e){
            throw new ReviewAlreadyExistsException("Review cannot be updated");
        }
    }

    // deletes review record from database
    public void deleteReview(Long id) {
        try {
            Review review = reviewRepository.findByReviewid(id);
            reviewRepository.delete(review);
        } catch (Exception e){
            throw new ReviewAlreadyExistsException("Review with: '" + id + "', not found");
        }
    }

    // find review by book
    public List<Review> findBookReviews(String book){
        try{
            List<Review> reviews = reviewRepository.findAllByBookname(book);
            return reviews;
        } catch(Exception e){
            throw new ReviewAlreadyExistsException("Reviews not found");
        }
    }
	
	// find if a review exists for testing purposes
    public boolean reviewExists(Long id){
        return reviewRepository.existsByReviewid(id);
    }
}
