package com.example.backend.service;

import com.example.backend.command.OrderCommand;
import com.example.backend.model.Orders;

import java.util.List;

public interface OrderService {

    void addOrder(Orders newOrder);
    Orders findByOrderId(Long orderId);
    List<Orders> findByUserId(Long userId);
    List<OrderCommand> findAllOrder();
}
