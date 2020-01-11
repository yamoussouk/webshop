package com.example.backend.command;

public class OrderDetailsCommand {

    private Long id;
    private String logoText;
    private String size;
    private String startingDay;
    private PlannerCommand plannerProduct;
    private LogoCommand logoProduct;
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
    public void setPlannerProduct(PlannerCommand product) {
        this.plannerProduct = product;
    }
    public void setLogoProduct(LogoCommand product) {
        this.logoProduct = product;
    }
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
    public PlannerCommand getPlannerProduct() {
        return this.plannerProduct;
    }
    public LogoCommand getLogoProduct() {
        return this.logoProduct;
    }
    public ImageCommand getImage() {
        return this.image;
    }
}