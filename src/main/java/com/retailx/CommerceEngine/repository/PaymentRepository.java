package com.retailx.CommerceEngine.repository;

import com.retailx.CommerceEngine.model.Payment;
import org.springframework.data.repository.CrudRepository;

public interface PaymentRepository extends CrudRepository<Payment, Long> {
}
