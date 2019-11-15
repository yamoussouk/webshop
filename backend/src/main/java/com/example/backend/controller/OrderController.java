package com.example.backend.controller;

import com.example.backend.command.OrderCommand;
import com.example.backend.model.Orders;
import com.example.backend.service.OrderService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/order")
public class OrderController {

    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
}

    //TODO: Exception handling

    /**
     * add new order
     * @param newOrder
     * @return added order
     */
    //TODO: send email
    @PostMapping("/add")
    public void addOrder(@RequestBody Orders newOrder) {
        orderService.addOrder(newOrder);
    }

    /**
     * query order by orderid
     * @param orderId
     * @return order
     */
    //TODO: checks user token
    @GetMapping("/{id}")
    public Orders findByOrderId(@PathVariable(name = "id") Long orderId) {
        return orderService.findByOrderId(orderId);
    }

    /**
     * queries orders by userid
     * @param userId
     * @return list of orders
     */
    //TODO: checks user token
    @GetMapping("/user/{id}")
    public List<Orders> findByUserId(@PathVariable(name = "id") Long userId) {
        return orderService.findByUserId(userId);
    }

    /**
     * queries all orders
     * @return list of orders
     */
    //TODO: add int pagination pathvariable
    //TODO: only for admin
    @GetMapping("/all")
    public List<OrderCommand> listAllOrder() {
        List<OrderCommand> temp = orderService.findAllOrder();
        return temp;
    }
}
