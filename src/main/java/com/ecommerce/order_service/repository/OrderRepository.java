package com.ecommerce.order_service.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.ecommerce.order_service.entity.Order;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
    // Advanced JPQL query to fetch orders within a date range
    @Query("SELECT o FROM Order o WHERE o.orderDate BETWEEN :startDate AND :endDate")
    List<Order> findOrdersWithinDateRange(Date startDate, Date endDate);
}
