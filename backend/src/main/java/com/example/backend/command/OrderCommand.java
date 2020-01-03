package com.example.backend.command;

import java.util.Date;
import java.util.List;

public class OrderCommand {

    private Long id;
    private double price;
    private double vat;
    private Date purchaseTime;
    private Long userid;
    private String email;
    private List<OrderDetailsCommand> orderDetails;
    private String coupon;

    public OrderCommand() {
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

    public void setEmail(String email) {
        this.email = email;
    }
    public void setOrderDetails(List<OrderDetailsCommand> details) {
        this.orderDetails = details;
    }
    public String getEmail() {
        return this.email;
    }
    public List<OrderDetailsCommand> getOrderDetails() {
        return this.orderDetails;
    }
    public void setCoupon(String coupon) {
        this.coupon = coupon;
    }
    public String getCoupon() {
        return this.coupon;
    }
    public void setVat(double vat) {
        this.vat = vat;
    }
    public double getVat() {
        return this.vat;
    }
}

