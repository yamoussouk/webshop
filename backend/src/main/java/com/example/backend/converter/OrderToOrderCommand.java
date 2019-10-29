package com.example.backend.converter;

import com.example.backend.command.OrderCommand;
import com.example.backend.model.Orders;
import com.example.backend.model.Product;
import com.example.backend.repository.ProductRepository;
import lombok.Synchronized;
import org.springframework.stereotype.Component;

import javax.validation.constraints.NotNull;

@Component
public class OrderToOrderCommand {

    private ProductRepository productRepository;
    private final PlannerToPlannerCommand productConverter;

    public OrderToOrderCommand(ProductRepository productRepository, PlannerToPlannerCommand productConverter) {
        this.productRepository = productRepository;
        this.productConverter = productConverter;
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
        orderCommand.setPurchaseTime(order.getPurchaseTime());
        orderCommand.setUserid(order.getUser().getId());
        for (Product product : order.getProducts()) {
            orderCommand.setOneProduct(productConverter.convert(product));
        }
        return orderCommand;
    }
}
