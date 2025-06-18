package com.retailx.CommerceEngine.repository;

import com.retailx.CommerceEngine.model.Order;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends CrudRepository<Order, Long> {
    List<Order> findByCustomer_CustomerId(Long customerCustomerId);
}
