package com.retailx.CommerceEngine.controller;

import com.retailx.CommerceEngine.exception.CustomerNotFoundException;
import com.retailx.CommerceEngine.exception.ProductNotFoundException;
import com.retailx.CommerceEngine.model.CustomerAddress;
import com.retailx.CommerceEngine.model.Order;
import com.retailx.CommerceEngine.model.dto.OrderRequestDTO;
import com.retailx.CommerceEngine.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/order")
public class OrderController {
    @Autowired
    private OrderService orderService;
    @PostMapping("/create")
    public Order createOrder(@RequestBody OrderRequestDTO orderRequestDTO) throws CustomerNotFoundException, ProductNotFoundException {
        return orderService.createOrder(orderRequestDTO);
    }   //NOTE: This is how its POST in postman
    // {
//    "customerId" : 3,
//            "customerAddress" : {
//        "street" : "N Brown St",
//                "city" : "Kansas City",
//                "state" : "Kansas",
//                "zipCode" : "61101"
//    },
//            "items" : [
//    {
//        "productId" : 3,
//            "orderQuantity" : 2
//    }
//    ],
//            "payment" : {
//        "paymentMethod" : "CREDIT",
//                "cardNumber" : 1233430009,
//                "cvv" : 500,
//                "expirationDate": 1130
//    }
//}
    @PutMapping("/{orderId}/status/cancel")     //Cancel an order
    public ResponseEntity<String> cancelling(@PathVariable Long orderId ){
        orderService.cancelOrder(orderId);
        return ResponseEntity.ok("Order cancelled successfully");
    }
    @PutMapping("/{orderId}/update/delivery/address")   //update delivery address
    public ResponseEntity<String> updateShippingAdd(@PathVariable Long orderId, @RequestBody CustomerAddress newAddress){
        orderService.updateShippingAddress(orderId, newAddress);
        return ResponseEntity.ok("Shipping address updated!");
    }
}
