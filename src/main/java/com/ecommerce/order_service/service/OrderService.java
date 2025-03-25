package com.ecommerce.order_service.service;

import java.util.Date;
import java.util.List;

import com.ecommerce.order_service.entity.Order;

public interface OrderService {
  Order createOrder(Order order);
  List<Order> getOrdersWithinRange(Date from, Date to);
}
