package com.example.backend.converter;

import com.example.backend.command.OrderCommand;
import com.example.backend.command.OrderDetailsCommand;
import com.example.backend.model.OrderDetails;
import com.example.backend.model.Orders;
import com.example.backend.model.Product;
import com.example.backend.repository.ProductRepository;
import lombok.Synchronized;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

import javax.validation.constraints.NotNull;

@Component
public class OrderToOrderCommand {

    private ProductRepository productRepository;
    private final PlannerToPlannerCommand productConverter;
    private final OrderDetailsToOrderDetailsCommand orderDetailsConverter;

    public OrderToOrderCommand(ProductRepository productRepository, PlannerToPlannerCommand productConverter,
    OrderDetailsToOrderDetailsCommand orderDetailsConverter) {
        this.productRepository = productRepository;
        this.productConverter = productConverter;
        this.orderDetailsConverter = orderDetailsConverter;
    }

    @Synchronized
    @NotNull
    public OrderCommand convert(Orders order) {
        if (order == null) {
            return null;
        }

        OrderCommand orderCommand = new OrderCommand();
        orderCommand.setId(order.getId());
        orderCommand.setPrice(order.getPrice());
        orderCommand.setVat(order.getVat());
        orderCommand.setPurchaseTime(order.getPurchaseTime());
        // orderCommand.setUserid(order.getUser().getId());
        orderCommand.setEmail(order.getEmail());
        List<OrderDetailsCommand> details = new ArrayList<>();
        for (OrderDetails detail : order.getOrderDetails()) {
            details.add(this.orderDetailsConverter.convert(detail));
        }
        orderCommand.setOrderDetails(details);
        orderCommand.setCoupon(order.getCoupon());
        return orderCommand;
    }
}
