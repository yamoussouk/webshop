package com.example.backend.command;

import com.example.backend.model.Image;
import com.example.backend.model.Logo;
import com.example.backend.model.Planner;

public class OrderDetailsCommand {

    private Long id;
    private String logoText;
    private String size;
    private String startingDay;
    // private Planner plannerProduct;
    // private Logo logoProduct;
    private PlannerCommand plannerProduct;
    private LogoCommand logoProduct;
    // private Image image;
    private ImageCommand image;

    public OrderDetailsCommand() {
    }

    public void setId(Long id) {
        this.id = id;
    }
    public void setLogoText(String text) {
        this.logoText = text;
    }
    public void setSize(String text) {
        this.size = text;
    }
    public void setStartingDay(String text) {
        this.startingDay = text;
    }
    // public void setPlannerProduct(Planner product) {
    //     this.plannerProduct = product;
    // }
    // public void setLogoProduct(Logo product) {
    //     this.logoProduct = product;
    // }
    public void setPlannerProduct(PlannerCommand product) {
        this.plannerProduct = product;
    }
    public void setLogoProduct(LogoCommand product) {
        this.logoProduct = product;
    }
    // public void setImage(Image i) {
    //     this.image = i;
    // }
    public void setImage(ImageCommand i) {
        this.image = i;
    }
    public Long getId() {
        return this.id;
    }
    public String getLogoText() {
        return this.logoText;
    }
    public String getSize() {
        return this.size;
    }
    public String getStartingDay() {
        return this.startingDay;
    }
    // public Planner getPlannerProduct() {
    //     return this.plannerProduct;
    // }
    // public Logo getLogoProduct() {
    //     return this.logoProduct;
    // }
    public PlannerCommand getPlannerProduct() {
        return this.plannerProduct;
    }
    public LogoCommand getLogoProduct() {
        return this.logoProduct;
    }
    // public Image getImage() {
    //     return this.image;
    // }
    public ImageCommand getImage() {
        return this.image;
    }
}