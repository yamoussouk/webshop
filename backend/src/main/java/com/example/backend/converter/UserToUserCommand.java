package com.example.backend.converter;

import com.example.backend.command.UserCommand;
import com.example.backend.model.User;
import lombok.Synchronized;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import javax.validation.constraints.NotNull;

@Component
public class UserToUserCommand implements Converter<User, UserCommand> {

    private final PlannerToPlannerCommand productConverter;

    public UserToUserCommand(PlannerToPlannerCommand productConverter) {
        this.productConverter = productConverter;
    }

    @Synchronized
    @NotNull
    @Override
    public UserCommand convert(User user) {
        if (user == null) {
            return null;
        }

        UserCommand userCommand = new UserCommand();
        userCommand.setId(user.getId());
        userCommand.setEmail(user.getEmail());
        userCommand.setFirstname(user.getFirstname());
        userCommand.setLastname(user.getLastname());
        userCommand.setRole(user.getRoles().iterator().next().getName());
        /*if (user.getProducts() != null && user.getProducts().size() > 0) {
            user.getProducts()
                    .forEach(product -> userCommand.setProduct(productConverter.convert(product)));
        }*/
        return userCommand;
    }
}
