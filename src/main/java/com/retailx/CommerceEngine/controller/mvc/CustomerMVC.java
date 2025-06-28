package com.retailx.CommerceEngine.controller.mvc;

import com.retailx.CommerceEngine.model.Customer;
import com.retailx.CommerceEngine.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/customer")
public class CustomerMVC {
    @Autowired
    private CustomerService customerService;
    @GetMapping("/register")
    public String showRegistrationForm(Model model){
        model.addAttribute("customer", new Customer());
        return "register";
    }
    @PostMapping("/register")
    public String registerCustomer(@ModelAttribute Customer customer, Model model) {
        customerService.enrollCustomer(customer);
        model.addAttribute("message", "Customer registered successfully!");
        return "success"; // success.html
    }
}
