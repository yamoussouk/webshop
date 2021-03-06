package com.example.backend.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.example.backend.model.Mail;
import com.example.backend.model.Orders;
import com.example.backend.repository.OrdersRepository;
import com.example.backend.repository.UserRepository;

import org.springframework.stereotype.Service;

@Service
public class OrderServiceImpl implements OrderService {

    private OrdersRepository ordersRepository;
    private UserRepository userRepository;
    private EmailService mailService;

    public OrderServiceImpl(OrdersRepository ordersRepository, UserRepository userRepository, EmailService mailService) {
        this.ordersRepository = ordersRepository;
        this.userRepository = userRepository;
        this.mailService = mailService;
    }

    @Override
    public void addOrder(Orders newOrder) {
        Orders order = this.ordersRepository.save(newOrder);
        // no need mail currently!!!
        // if (order != null) {
        //     Mail orderSuccess = new Mail();
        //     orderSuccess.setFrom("valaki@gmail.com");
        //     orderSuccess.setTo(order.getEmail());
        //     orderSuccess.setSubject("Item successfully ordered!");
        //     Map<String, Object> model = new HashMap<>();
        //     model.put("email", order.getEmail());
        //     model.put("products", order.getProducts());
        //     model.put("price", order.getPrice());
        //     model.put("purchaseTime", order.getPurchaseTime());
        //     orderSuccess.setModel(model);
        //     mailService.sendEmail(orderSuccess, "order-success");
        // }
        // some size and starting day logic here
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

