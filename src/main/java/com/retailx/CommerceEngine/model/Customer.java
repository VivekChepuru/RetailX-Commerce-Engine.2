package com.retailx.CommerceEngine.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Customer {
    @Id
    private Long customerId;

    @Column(name = "first_name", nullable = false)
    private String firstName;

    @Column(length = 20)
    private String lastName;

    @Column(nullable = false)
    private String mobileNumber;

    @Column(unique = true)
    private String email;

    @OneToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "address_id", unique = true)
    private CustomerAddress customerAddress;
}