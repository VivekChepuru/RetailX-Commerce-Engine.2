package com.retailx.CommerceEngine.controller.mvc;

import com.retailx.CommerceEngine.model.dto.ReviewRequestDTO;
import com.retailx.CommerceEngine.service.ReviewsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/review")
public class ReviewMVC {
    @Autowired
    private ReviewsService reviewsService;

    /**
     * Handles submission of a product review.
     * Uses the Post/Redirect/Get (PRG) pattern to prevent duplicate form submissions.
     * After successfully adding a review, it redirects to the review form page,
     * ensuring that refreshing the browser does not resubmit the review.
     */
    @PostMapping("/write")
    public String addReview(@ModelAttribute ReviewRequestDTO reviewRequestDTO, RedirectAttributes redirectAttributes) {
        reviewsService.addReview(reviewRequestDTO);
        redirectAttributes.addFlashAttribute("successMessage", "Review submitted successfully!");
        return "redirect:/review/write";
    }
    //Helper method for the above addReview method
    //This ensures that when the user opens the form, Thymeleaf knows what reviewRequestDTO is.
    @GetMapping("/write")
    public String showReviewForm(Model model) {
        model.addAttribute("reviewRequestDTO", new ReviewRequestDTO());
        return "review_write"; // this should match the HTML file name (review_write.html)
    }

    @GetMapping("/search")
    public String searchReviewForm() {
        return "searchReviews"; // the form page
    }
    //The below findReviews method returns the HTML as a response for the above method
    @GetMapping("/find")
    public String findReviews(@RequestParam Long productId, Model model) {
        model.addAttribute("reviews", reviewsService.getReviewsByProductId(productId));
        return "reviewsList"; // list page to show reviews
    }
}
