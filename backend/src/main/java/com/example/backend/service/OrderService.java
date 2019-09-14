package com.example.backend.service;

import com.example.backend.model.Orders;

import java.util.List;
import java.util.Map;

public interface OrderService {

    Map<String, Object> addOrder(Map<String, Object> newOrder);
    Orders findByOrderId(Long orderId);
    List<Orders> findByUserId(Long userId);
    List<Orders> findAllOrder();
}
