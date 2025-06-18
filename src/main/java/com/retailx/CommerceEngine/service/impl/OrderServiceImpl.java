package com.retailx.CommerceEngine.service.impl;

import com.retailx.CommerceEngine.exception.CustomerNotFoundException;
import com.retailx.CommerceEngine.exception.ProductNotFoundException;
import com.retailx.CommerceEngine.exception.ProductOutOfStockException;
import com.retailx.CommerceEngine.model.*;
import com.retailx.CommerceEngine.model.dto.OrderItemDTO;
import com.retailx.CommerceEngine.model.dto.OrderRequestDTO;
import com.retailx.CommerceEngine.model.enums.OrderStatus;
import com.retailx.CommerceEngine.repository.CustomerRepository;
import com.retailx.CommerceEngine.repository.OrderRepository;
import com.retailx.CommerceEngine.repository.PaymentRepository;
import com.retailx.CommerceEngine.repository.ProductRepository;
import com.retailx.CommerceEngine.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class OrderServiceImpl implements OrderService {
    @Autowired
    private CustomerRepository customerRepository;
    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private PaymentRepository paymentRepository;

    @Override
    public Order createOrder(OrderRequestDTO orderRequestDTO) {
        //validate customer with ID
        Customer customer = customerRepository.findById(orderRequestDTO.getCustomerId())
                .orElseThrow(()->new CustomerNotFoundException("Customer with ID: " + orderRequestDTO.getCustomerId()+" is not found"));

        Order order = new Order();
        order.setCustomer(customer);    //populating/ adding customer to order
        order.setCustomerAddress(orderRequestDTO.getShippingAddress());    //address to order
        order.setCreatedAt(LocalDateTime.now());    //Setting date&time
        order.setStatus(OrderStatus.CREATED);   //status
        //Handle Order Items
        for (OrderItemDTO itemDTO : orderRequestDTO.getItems()){   //validating the product with ID
            Product product = productRepository.findById(itemDTO.getProductId())
                    .orElseThrow(()-> new ProductNotFoundException("Product not found"));

            //Validate the requested quantity <= available quantity
            //Throw an exception with valid message
            if (itemDTO.getOrderQuantity()>product.getAvailableQuantity()){
                throw new ProductOutOfStockException("Product : "+product.getProductName()+" is out of stock. Only "+itemDTO.getOrderQuantity()+ " left");
            }
            //Update the stock or Quantity of that product
            product.setAvailableQuantity(product.getAvailableQuantity() - itemDTO.getOrderQuantity());
            productRepository.save(product);    //Subtracting and saving the new quantity

            OrderItem item = new OrderItem();   //Adding the product to the order
            item.setProduct(product);
            item.setOrderQuantity(itemDTO.getOrderQuantity());  //Adding requested quantity
            item.setOrder(order);   //mapping order on the many side
            order.getItems().add(item);
        }
        //Set delivery date
        order.setPromisedDeliveryDate(LocalDate.now().plusDays(14));//setting it to two weeks
        //Calculating costs
        double subTotal = 0.0;
        for (OrderItem item : order.getItems()){    //iterates the items
            double itemTotal = item.getProduct().getPrice()*item.getOrderQuantity();
                                            //product price * quantity
            subTotal += itemTotal;  //adding and assigning
        }
        double tax = subTotal * 0.08;
        double shippingCharges = subTotal>35.0 ? 7.00 : 0.00;
                                //if >35 = 7$ else 0$
        double total = subTotal+tax+shippingCharges;
        order.setSubTotal(subTotal);    //setting to order
        order.setTax(tax);
        order.setShippingCharges(shippingCharges);
        order.setTotal(total);
        //Validate and save payment
        Payment payment = orderRequestDTO.getPayment();
        validatePayment(payment);
        paymentRepository.save(payment);

        order.setCustomerAddress(orderRequestDTO.getShippingAddress());//populating
        return orderRepository.save(order);
    }
    //Validation to avoid invalid payment details
    private void validatePayment(Payment payment) {
        if (payment.getPaymentMethod() == null) {
            throw new IllegalArgumentException("Payment method is required.");
        }
        if (payment.getCardNumber() == null || payment.getCardNumber().length() < 4) {
            throw new IllegalArgumentException("Invalid card number.");
        }
        if (payment.getCvv() == null || payment.getCvv().length() < 3) {
            throw new IllegalArgumentException("Invalid CVV.");
        }
        if (payment.getExpirationDate() == null || payment.getExpirationDate().isEmpty()) {
            throw new IllegalArgumentException("Expiration date is required.");
        }
    }

    @Override
    public void cancelOrder(Long orderId) {
        Optional<Order> orderOptional = orderRepository.findById(orderId);
        if (orderOptional.isEmpty()){
            throw new IllegalArgumentException("order with Id :"+orderId+" not found");
        }
        Order order = orderOptional.get();
        order.setStatus(OrderStatus.CANCELLED); //Setting status as cancelled
        orderRepository.save(order);
    }

    @Override
    public void updateShippingAddress(Long orderId, CustomerAddress newAddress) {
        Optional<Order> optionalOrder = orderRepository.findById(orderId);
        //Order Id Validation
        if (optionalOrder.isEmpty()){
            throw new IllegalArgumentException("Order with ID: "+orderId+" not found");
        }
        //Now if present
        Order order = optionalOrder.get();  //Get the required order for update
        order.setCustomerAddress(newAddress);
        orderRepository.save(order);
    }

    @Override
    public Optional<Order> getOrderById(Long orderId) {
        return orderRepository.findById(orderId);
    }

    @Override
    public List<Order> getOrdersByCustomerId(Long customerId) {
        return orderRepository.findByCustomer_CustomerId(customerId);
    }

    @Override
    public void markOrderAsPaid(Long orderId, Payment payment) {

    }
    @Override
    public void updateOrderStatus(Long orderId, String status) {

    }
}
