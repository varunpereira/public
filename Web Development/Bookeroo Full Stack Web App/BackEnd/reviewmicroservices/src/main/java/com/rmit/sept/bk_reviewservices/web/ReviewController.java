package com.rmit.sept.bk_reviewservices.web;

import com.rmit.sept.bk_reviewservices.model.Review;
import com.rmit.sept.bk_reviewservices.services.MapValidationErrorService;
import com.rmit.sept.bk_reviewservices.services.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/api/review")
public class ReviewController {

    @Autowired
    private MapValidationErrorService mapValidationErrorService;

    @Autowired
    private ReviewService reviewService;

    // adds review
    @PostMapping("/addReview")
    public ResponseEntity<?> addReview(@Valid @RequestBody Review review, BindingResult result){
        ResponseEntity<?> errorMap = mapValidationErrorService.MapValidationService(result);
        if (errorMap != null) {
            return errorMap;
        }

        Review newReview = reviewService.saveReview(review);

        return new ResponseEntity<Review>(newReview, HttpStatus.CREATED);
    }

    // updates review
    @PatchMapping("/updateReview/{bookname}")
    public ResponseEntity<?> updateReviewDetails(@PathVariable(value = "bookname") String bookname,
                                               @Valid @RequestBody Review review, BindingResult result){
        ResponseEntity<?> errorMap = mapValidationErrorService.MapValidationService(result);
        if (errorMap != null) {
            return errorMap;
        }

        Review updateReview = reviewService.updateReview(review, bookname);

        return new ResponseEntity<Review>(updateReview, HttpStatus.CREATED);
    }

    // deletes review
    @DeleteMapping("/deleteReview/{id}")
    public ResponseEntity<String> deleteReviewByID (@PathVariable(value = "id") Long id) {
        try {
            reviewService.deleteReview(id);
            return new ResponseEntity<String>("Review with id: " + id  + " deleted", HttpStatus.OK);
        } catch (Exception e){
            return new ResponseEntity<String>("Review with id: '" + id + "', does not exist", HttpStatus.BAD_REQUEST);
        }
    }

    // finds book reviews
    @GetMapping("/findReviews/{book}")
    public List<Review> findReviewsByBook(@PathVariable(value = "book") String book) {
        return reviewService.findBookReviews(book);
    }
}
