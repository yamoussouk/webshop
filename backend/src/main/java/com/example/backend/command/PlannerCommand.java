package com.example.backend.command;

import com.example.backend.model.Category;
import com.example.backend.command.ImageCommand;

import java.util.Set;

public class PlannerCommand {

    private Long id;
    private String name;
    private String shortDescription;
    private String longDescription;
    private double price;
    private Set<Category> category;
    private Set<String> categories;
    private Set<ImageCommand> image;
    private int quantity;
    private Boolean enabled;
    private String downloadLink;
    private String size;
    private String startingDay;

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public PlannerCommand() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getShortDescription() {
        return shortDescription;
    }

    public void setShortDescription(String shortDescription) {
        this.shortDescription = shortDescription;
    }

    public String getLongDescription() {
        return longDescription;
    }

    public void setLongDescription(String longDescription) {
        this.longDescription = longDescription;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public Set<Category> getCategory() {
        return this.category;
    }

    public void setCategory(Set<Category> category) {
        this.category = category;
    }

    public void setCategories(Set<String> category) {
        this.categories = category;
    }

    public Set<String> getCategories() {
        return this.categories;
    }

    public Set<ImageCommand> getImage() {
        return image;
    }

    public void setImage(Set<ImageCommand> image) {
        this.image = image;
    }

    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }

    public Boolean getEnabled() {
        return this.enabled;
    }

    public String getDownloadLink() {
        return this.downloadLink;
    }

    public void setDownloadLink(String link) {
        this.downloadLink = link;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public String getSize() {
        return this.size;
    }

    public void setStartingDay(String day) {
        this.startingDay = day;
    }

    public String getStartingDay() {
        return this.startingDay;
    }
}

