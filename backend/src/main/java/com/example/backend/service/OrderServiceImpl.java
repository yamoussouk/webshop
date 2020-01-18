package com.example.backend.service;

import java.util.ArrayList;
import java.util.List;

import com.example.backend.command.OrderCommand;
import com.example.backend.converter.OrderToOrderCommand;
import com.example.backend.model.Logo;
import com.example.backend.model.OrderDetails;
import com.example.backend.model.Orders;
import com.example.backend.model.Planner;
import com.example.backend.repository.OrderDetailsRepository;
import com.example.backend.repository.OrdersRepository;
import com.example.backend.repository.UserRepository;

import org.springframework.stereotype.Service;

@Service
public class OrderServiceImpl implements OrderService {

    private OrdersRepository ordersRepository;
    private UserRepository userRepository;
    private OrderDetailsRepository orderDetailsRepository;
    private OrderToOrderCommand ordersConverter;

    public OrderServiceImpl(OrdersRepository ordersRepository, 
    UserRepository userRepository, EmailService mailService, OrderDetailsRepository orderDetailsRepository,
    OrderToOrderCommand ordersConverter) {
        this.ordersRepository = ordersRepository;
        this.userRepository = userRepository;
        this.orderDetailsRepository = orderDetailsRepository;
        this.ordersConverter = ordersConverter;
    }

    @Override
    public void addOrder(Orders newOrder) {
        Orders order = this.ordersRepository.save(newOrder);
        for (Planner p : order.getPlanners()) {
            OrderDetails o = new OrderDetails();
            o.setProductID(p.getId());
            o.setSize(p.getSize());
            o.setStartingDay(p.getStartingDay());
            o.setOrders(order);
            OrderDetails pl = this.orderDetailsRepository.save(o);
            order.setOrderDetails(pl);
        }
        // order = this.ordersRepository.save(order);
        for (Logo l : order.getLogos()) {
            OrderDetails o = new OrderDetails();
            o.setProductID(l.getId());
            o.setLogoText(l.getLogoText());
            o.setOrders(order);
            OrderDetails lo = this.orderDetailsRepository.save(o);
            order.setOrderDetails(lo);
        }
        order = this.ordersRepository.save(order);
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
    public List<OrderCommand> findAllOrder() {
        List<Orders> all = this.ordersRepository.findAll();
        if (all != null) {
            List<OrderCommand> allConvertedOrders = new ArrayList<>();
            for (Orders order : all) {
                allConvertedOrders.add(this.ordersConverter.convert(order));
            }
            return allConvertedOrders;
        } else {
            return null;
        }
    }
}

