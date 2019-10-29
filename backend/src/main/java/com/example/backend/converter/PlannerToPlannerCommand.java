package com.example.backend.converter;

import com.example.backend.command.ImageCommand;
import com.example.backend.command.PlannerCommand;
import com.example.backend.model.Category;
import com.example.backend.model.Image;
import com.example.backend.model.Planner;
import lombok.Synchronized;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.validation.constraints.NotNull;

@Component
public class PlannerToPlannerCommand implements Converter<Planner, PlannerCommand> {

    final ImageToImageCommand imageToImageCommand;

    public PlannerToPlannerCommand() {
        this.imageToImageCommand = new ImageToImageCommand();
    }

    @Synchronized
    @NotNull
    @Override
    public PlannerCommand convert(Planner planner) {
        if (planner == null) {
            return null;
        }

        PlannerCommand PlannerCommand = new PlannerCommand();
        PlannerCommand.setId(planner.getId());
        PlannerCommand.setCategory(planner.getCategory());
        Set<String> categories = new HashSet<String>();
        for (Category c : planner.getCategory()) {
            categories.add(c.getName());
        }
        PlannerCommand.setCategories(categories);
        PlannerCommand.setShortDescription(planner.getShortDescription());
        PlannerCommand.setLongDescription(planner.getLongDescription());
        PlannerCommand.setName(planner.getName());
        PlannerCommand.setPrice(planner.getPrice());
        PlannerCommand.setQuantity(planner.getQuantity());
        Set<ImageCommand> images = new HashSet<ImageCommand>();
        for (Image i : planner.getImages()) {
            images.add(this.imageToImageCommand.convert(i));
        }
        PlannerCommand.setImage(images);
        PlannerCommand.setEnabled(planner.getEnabled());
        PlannerCommand.setDownloadLink(planner.getDownloadLink());
        return PlannerCommand;
    }
}

