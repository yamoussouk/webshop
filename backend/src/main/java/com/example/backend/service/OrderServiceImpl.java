package com.example.backend.service;

import com.example.backend.converter.JsonConverter;
import com.example.backend.model.Orders;
import com.example.backend.repository.OrdersRepository;
import com.example.backend.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class OrderServiceImpl implements OrderService {

    private OrdersRepository ordersRepository;
    private UserRepository userRepository;

    public OrderServiceImpl(OrdersRepository ordersRepository, UserRepository userRepository) {
        this.ordersRepository = ordersRepository;
        this.userRepository = userRepository;
    }

    @Override
    public Map<String, Object> addOrder(Map<String, Object> newOrder) {
        return JsonConverter.convertOrderToJson(
                this.ordersRepository.save(
                        JsonConverter.convertJsonToOrder(newOrder)
                )
        );
    }

    @Override
    public Orders findByOrderId(Long orderId) {
        return this.ordersRepository.findById(orderId).get();
    }

    @Override
    public List<Orders> findByUserId(Long userId) {
        return this.ordersRepository.findByUser(this.userRepository.findById(userId).get());
    }

    @Override
    public List<Orders> findAllOrder() {
        if (this.ordersRepository.findAll() != null) {
            return this.ordersRepository.findAll();
        } else {
            return null;
        }
    }
}

