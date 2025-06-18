package com.retailx.CommerceEngine.controller;

import com.retailx.CommerceEngine.model.Product;
import com.retailx.CommerceEngine.service.ProductService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Slf4j
@RestController
@RequestMapping("/product")
public class ProductController {
    @Autowired
    private ProductService productService;

    @PostMapping("/onboard")
    public ResponseEntity<Product> onboard(@RequestBody Product product){
        Product saved = productService.onboardProduct(product);
        log.info("Product Onboarded");
        return ResponseEntity.ok(saved);
    }
    @PutMapping("/{productId}/update/stock")
    public ResponseEntity<Product> stockUp(@PathVariable Long productId, @RequestBody Long quantity){
        Product updatedProduct = productService.stockUpProduct(productId, quantity);
        return ResponseEntity.ok(updatedProduct);
    }
    @DeleteMapping("/{productId}/delete")
    public ResponseEntity<Product> delete(@PathVariable Long productId){
        Product dltProduct = productService.deleteProduct(productId);
        return ResponseEntity.ok(dltProduct);
    }
    @GetMapping("/{productId}/get")
    public ResponseEntity<Optional<Product>> getProduct(@PathVariable Long productId){
        Optional<Product> getProduct = productService.getProductById(productId);
        return ResponseEntity.ok(getProduct);
    }
    @GetMapping("/get/all")
    public ResponseEntity<Product> getAll(){
        List<Product> allProd = productService.getAllProducts();
        return ResponseEntity.ok((Product) allProd);
    }
    @PutMapping("{productId}/update/price")
    public ResponseEntity<String> updatePrice(@PathVariable Long productId, @RequestBody Double newPrice){
        productService.updateProductPrice(productId, newPrice);
        return ResponseEntity.ok("Product price updated");
    }
}
