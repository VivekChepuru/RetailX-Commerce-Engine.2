package com.retailx.CommerceEngine.controller.mvc;

import com.retailx.CommerceEngine.model.Customer;
import com.retailx.CommerceEngine.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/customers")
public class CustomerMVC {
    @Autowired
    private CustomerService customerService;

    @GetMapping("/enroll")
    public String showRegistrationForm(Model model){
        model.addAttribute("customer", new Customer());
        return "register";
    }
    @PostMapping("/enroll")
    public String registerCustomer(@ModelAttribute Customer customer, Model model) {
        customerService.enrollCustomer(customer);
        model.addAttribute("message", "Customer registered successfully!");
        return "success"; // success.html
    }
    @PutMapping("/{customerId}/update/contact")
    public String updatePhone(@PathVariable Long customerId, @RequestParam String newNumber, Model model){
        customerService.updatePhoneNo(customerId, newNumber);
        model.addAttribute("message", "Contact updated successfully!");
        return "newMobile";
    }
    @GetMapping("/{customerId}/find")
    public String searchById(@PathVariable Long customerId, Model model){
        Customer customer = customerService.getCustomerById(customerId);
        model.addAttribute("customer", customer);
        return "find";
    }
}
