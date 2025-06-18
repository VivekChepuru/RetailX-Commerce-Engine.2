package com.retailx.CommerceEngine.controller;

import com.retailx.CommerceEngine.model.Reviews;
import com.retailx.CommerceEngine.model.dto.ReviewRequestDTO;
import com.retailx.CommerceEngine.service.ReviewsService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/reviews")
public class ReviewController {
    @Autowired
    private ReviewsService reviewsService;
    @PostMapping("/write")
    public ResponseEntity<Reviews> addReview(@RequestBody ReviewRequestDTO reviewRequestDTO){
        Reviews reviews = reviewsService.addReview(reviewRequestDTO);
        log.info("Review Posted");
        return ResponseEntity.ok(reviews);
    }
    @GetMapping("/find/product/{productId}")
    public ResponseEntity<List<Reviews>> getByProductId(@PathVariable Long productId){
        List<Reviews> reviews = reviewsService.getReviewsByProductId(productId);
        return ResponseEntity.ok(reviews);
    }
    @GetMapping("/find/customer/{customerId}")
    public ResponseEntity<List<Reviews>> getByCustomerId(@PathVariable Long customerId){
        List<Reviews> reviews = reviewsService.getReviewsByCustomerId(customerId);
        return ResponseEntity.ok(reviews);
    }
}
