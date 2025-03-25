package com.ecommerce.order_service.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;

import com.ecommerce.order_service.dto.Product;

@FeignClient("PRODUCT-SERVICE")
public interface ProductClient {
    @GetMapping("/api/products/{id}")
    Product getProductById(@PathVariable Long id);

    @PutMapping("/api/products/{id}/reduceQuantity/{quantity}")
    Product reduceProductQuantity(@PathVariable Long id, @PathVariable int quantity);
}
