package com.example.backend.converter;

import com.example.backend.command.UserCommand;
import com.example.backend.model.Role;
import com.example.backend.model.User;
import lombok.Synchronized;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import javax.validation.constraints.NotNull;
import java.util.Arrays;

@Component
public class UserCommandToUser implements Converter<UserCommand, User> {

    private final PlannerCommandToPlanner converter;

    public UserCommandToUser(PlannerCommandToPlanner converter) {
        this.converter = converter;
    }

    @Synchronized
    @NotNull
    @Override
    public User convert(UserCommand userCommand) {
        if (userCommand == null) {
            return null;
        }

        User user = new User();
        user.setId(userCommand.getId());
        user.setLastname(userCommand.getLastname());
        user.setFirstname(userCommand.getFirstname());
        user.setEmail(userCommand.getEmail());
        /*if(userCommand.getProducts() != null && userCommand.getProducts().size() > 0) {
            userCommand.getProducts()
                    .forEach(product -> user.setProducts(converter.convert(product)));
        }*/
        user.setRoles(Arrays.asList(new Role(userCommand.getRole())));
        return user;
    }
}
