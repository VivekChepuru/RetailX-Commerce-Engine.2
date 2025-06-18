package com.retailx.CommerceEngine.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.retailx.CommerceEngine.model.enums.ReviewRating;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;


@Entity
@Getter
@Setter
public class Reviews {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long reviewId;

    //Reviewer's comment on product
    @Column(nullable = false, length = 1000)
    private String comment;

    //1 to 5 stars
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ReviewRating rating;

    //Timestamp of when the review was created
    private LocalDateTime reviewedAt;

    //Many reviews belong to one product
    @ManyToOne
    @JoinColumn(name = "product_id", nullable = false)
    @JsonIgnore
    private Product product;

    @ManyToOne  //Who reviewed
    @JoinColumn(name = "customer_id")
    private Customer customer;
}
