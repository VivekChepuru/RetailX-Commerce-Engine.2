package com.retailx.CommerceEngine.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice   //Global Exception Handling
public class APIExceptionHandler {
    @ExceptionHandler
    public ResponseEntity<String> handleIllegalArgumentException(IllegalArgumentException ie){
        return ResponseEntity.badRequest().body(ie.getMessage());
    }
    @ExceptionHandler(CustomerNotFoundException.class)
    public ResponseEntity<String> handleCustomerNotFoundException(CustomerNotFoundException ce){
        return new ResponseEntity<>(ce.getMessage(), HttpStatus.NOT_FOUND);
    }
    @ExceptionHandler
    public ResponseEntity<String> handleProductNotFoundException(ProductNotFoundException pe){
        return new ResponseEntity<>(pe.getMessage(), HttpStatus.NOT_FOUND);
    }
    @ExceptionHandler
    public ResponseEntity<String> handleProductOutOfStockException(ProductOutOfStockException se){
        return new ResponseEntity<>(se.getMessage(), HttpStatus.CONFLICT);
    }
}
