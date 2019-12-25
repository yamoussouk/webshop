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
import java.util.Arrays;
import java.util.Comparator;
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

    private Set<ImageCommand> sortImages(final Set<ImageCommand> images) {
        Set.sort(images, new Comparator<String>() {
            public int compare(final String s1, final String s2) {
                final String s11 = s1.split(".")[0];
                final String s111 = s11.split("_")[0];
                final String n1 = s111.split("_")[0] + s111.split("_")[1];
                final String s21 = s1.split(".")[0];
                final String s211 = s21.split("_")[0];
                final String n2 = s211.split("_")[0] + s211.split("_")[1];
                return Integer.valueOf(n1).compareTo(Integer.valueOf(n2));
            }
        });
        return images;
    }

    @Synchronized
    @NotNull
    @Override
    public PlannerCommand convert(final Planner planner) {
        if (planner == null) {
            return null;
        }

        final PlannerCommand PlannerCommand = new PlannerCommand();
        PlannerCommand.setId(planner.getId());
        PlannerCommand.setCategory(planner.getCategory());
        final Set<String> categories = new HashSet<String>();
        for (final Category c : planner.getCategory()) {
            categories.add(c.getName());
        }
        PlannerCommand.setCategories(categories);
        PlannerCommand.setShortDescription(planner.getShortDescription());
        PlannerCommand.setLongDescription(planner.getLongDescription());
        PlannerCommand.setName(planner.getName());
        PlannerCommand.setPrice(planner.getPrice());
        PlannerCommand.setQuantity(planner.getQuantity());
        final List<ImageCommand> images = new ArrayList<ImageCommand>();
        for (final Image i : planner.getImages()) {
            images.add(this.imageToImageCommand.convert(i));
        }
        // Set<ImageCommand> sortedImages = sortImages(images);
        PlannerCommand.setImage(images);
        PlannerCommand.setEnabled(planner.getEnabled());
        PlannerCommand.setDownloadLink(planner.getDownloadLink());
        return PlannerCommand;
    }
}

