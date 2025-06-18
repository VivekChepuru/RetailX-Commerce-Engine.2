package com.retailx.CommerceEngine.controller;

import com.retailx.CommerceEngine.model.Customer;
import com.retailx.CommerceEngine.service.CustomerService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/customer")
public class CustomerController {
    @Autowired
    private CustomerService customerService;

    @PostMapping("/enroll") //Add a new Customer
    public ResponseEntity<Customer> enroll(@RequestBody Customer customer) {//Everything in the request body is
        Customer enrolled = customerService.enrollCustomer(customer);   //converted into customer obj
        log.info("Customer Enrolled");
        return ResponseEntity.ok(enrolled);
    }
    @PutMapping("/{customerId}/update/contact") //Update phone number   //Just enter RAW number in JSON
    public ResponseEntity<Customer> updatePhone(@PathVariable Long customerId, @RequestBody String newNumber) {
        Customer updatedCustomer = customerService.updatePhoneNo(customerId, newNumber);
        log.info("Contact Updated Successfully");
        return ResponseEntity.ok(updatedCustomer);
    }
    @GetMapping("/{customerId}/find")   //Search a customer
    public Customer searchById(@PathVariable Long customerId){
        return customerService.getCustomerById(customerId);
    }
    @DeleteMapping("/{customerId}/delete")  //Delete a customer
    public Customer deleteCustomer(@PathVariable Long customerId){
        return customerService.deleteCustomer(customerId);
    }

}
