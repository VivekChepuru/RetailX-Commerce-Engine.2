package com.retailx.CommerceEngine.model;

import com.retailx.CommerceEngine.model.enums.PaymentMethod;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Entity
@Getter
@Setter
public class Payment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long transactionId;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private PaymentMethod paymentMethod;

    private String cardNumber;

    private String cvv;

    private String expirationDate;

    private String confirmationCode;

    @PrePersist
    public void generateConfirmationCode() {    //Generates a unique UUID for confirmation_code
        this.confirmationCode = UUID.randomUUID().toString().substring(0, 6).toUpperCase();
    }

}
