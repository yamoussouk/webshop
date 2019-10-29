package com.example.backend.command;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class OrderCommand {

    private Long id;
    private double price;
    private Date purchaseTime;
    private Long userid;
    private List<PlannerCommand> products;

    public OrderCommand() {
        this.products = new ArrayList<>();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public Date getPurchaseTime() {
        return purchaseTime;
    }

    public void setPurchaseTime(Date purchaseTime) {
        this.purchaseTime = purchaseTime;
    }

    public Long getUserid() {
        return userid;
    }

    public void setUserid(Long userid) {
        this.userid = userid;
    }

    public List<PlannerCommand> getProducts() {
        return products;
    }

    public void setProducts(List<PlannerCommand> products) {
        this.products = products;
    }

    public void setOneProduct(PlannerCommand product) {
        this.products.add(product);
    }
}

