package com.ecommerce.order_service.service;

import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ecommerce.order_service.client.ProductClient;
import com.ecommerce.order_service.dto.Product;
import com.ecommerce.order_service.entity.Order;
import com.ecommerce.order_service.entity.OrderItem;
import com.ecommerce.order_service.exception.ProductUnavailableException;
import com.ecommerce.order_service.repository.OrderRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final ProductClient productClient; // Inject Feign Client

    @Override
    @Transactional
    public Order createOrder(Order order) {
        order.setOrderDate(new Date());

        // Validate product details using Product Service
        for (OrderItem item : order.getOrderItems()) {
            Product product;
            try {
                product = productClient.getProductById(item.getProductId());
            } catch (Exception e) {
                throw new ProductUnavailableException("Failed to fetch product details: " + e.getMessage(), e);
            }

            if (product == null || product.getQuantity() < item.getQuantity()) {
                throw new ProductUnavailableException("Product unavailable or insufficient quantity");
            }

            // Update order item price from Product Service
            item.setPrice(product.getPrice());
            item.setOrder(order);

            // Reduce quantity in Product Service clearly here
            productClient.reduceProductQuantity(product.getId(), item.getQuantity());
        }

        // Calculate total amount clearly
        double totalAmount = order.getOrderItems()
                .stream()
                .mapToDouble(i -> i.getPrice() * i.getQuantity())
                .sum();
        order.setTotalAmount(totalAmount);

        return orderRepository.save(order);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Order> getOrdersWithinRange(Date from, Date to) {
        return orderRepository.findOrdersWithinDateRange(from, to);
    }
}
