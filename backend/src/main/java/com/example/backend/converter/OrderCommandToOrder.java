package com.example.backend.converter;

import com.example.backend.command.OrderCommand;
import com.example.backend.command.ProductCommand;
import com.example.backend.model.Orders;
import com.example.backend.repository.UserRepository;
import org.springframework.stereotype.Component;

@Component
public class OrderCommandToOrder {

    private UserToUserCommand converter;
    private final ProductCommandToProduct productConverter;
    private final UserRepository userRepository;

    public OrderCommandToOrder(UserToUserCommand converter, UserRepository userRepository, ProductCommandToProduct productConverter) {
        this.converter = converter;
        this.userRepository = userRepository;
        this.productConverter = productConverter;
    }

    public Orders convert(OrderCommand orderCommand) {
        if (orderCommand == null) {
            return null;
        }

        Orders order = new Orders();
        order.setId(orderCommand.getId());
        order.setPrice(orderCommand.getPrice());
        order.setPurchaseTime(orderCommand.getPurchaseTime());
        order.setUser(userRepository.findById(orderCommand.getId()).get());
        for (ProductCommand product : orderCommand.getProducts()) {
            order.setOneProduct(productConverter.convert(product));
        }
        return order;
    }
}
