package com.example.backend.converter;

import javax.validation.constraints.NotNull;

import com.example.backend.command.OrderDetailsCommand;
import com.example.backend.model.Image;
import com.example.backend.model.Logo;
import com.example.backend.model.OrderDetails;
import com.example.backend.model.Planner;
import com.example.backend.repository.LogoRepository;
import com.example.backend.repository.PlannerRepository;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import lombok.Synchronized;

@Component
public class OrderDetailsToOrderDetailsCommand implements Converter<OrderDetails, OrderDetailsCommand> {

    private final LogoRepository logoRepository;
    private final PlannerRepository plannerRepository;

    public OrderDetailsToOrderDetailsCommand(LogoRepository logoRepository, PlannerRepository plannerRepository) {
        this.plannerRepository = plannerRepository;
        this.logoRepository = logoRepository;
    }

    @Synchronized
    @NotNull
    @Override
    public OrderDetailsCommand convert(OrderDetails details) {
        if (details == null) {
            return null;
        }
        OrderDetailsCommand o = new OrderDetailsCommand();
        o.setId(details.getId());
        if (details.getLogoText() != null) {
            o.setLogoText(details.getLogoText());
        }
        if (details.getSize() != null) {
            o.setSize(details.getSize());
        }
        if (details.getStartingDay() != null) {
            o.setStartingDay(details.getStartingDay());
        }
        if (details.getLogoText() != null) {
            o.setLogoProduct(this.logoRepository.findById(details.getProductID()).get());
        } else {
            o.setPlannerProduct(this.plannerRepository.findById(details.getProductID()).get());
        }
        if (details.getLogoText() != null) {
            Logo logo = this.logoRepository.findById(details.getProductID()).get();
            Image im = logo.getImages().stream().filter(i -> i.getImageUrl().contains("cover")).findFirst().get();
            o.setImage(im);
        } else {
            Planner planner = this.plannerRepository.findById(details.getProductID()).get();
            Image im = planner.getImages().stream().filter(i -> i.getImageUrl().contains("cover")).findFirst().get();
            o.setImage(im);
        }
        return o;
    }
}