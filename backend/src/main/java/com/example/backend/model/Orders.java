package com.example.backend.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import javax.persistence.*;
import java.util.*;

@Entity
public class Orders {

    @Id
    @GeneratedValue
    private Long id;
    private double price;
    private String couponName;
 
    private Date purchaseTime;
    @ManyToOne(fetch = FetchType.LAZY)
    @JsonBackReference(value="user")
    private User user;
    private String email;
    
    @ManyToMany(fetch = FetchType.LAZY)
    @JsonBackReference(value="planners")
    private List<Planner> planners = new ArrayList<>();

    @ManyToMany(fetch = FetchType.LAZY)
    @JsonBackReference(value="logos")
    private List<Logo> logos = new ArrayList<>();

    @OneToMany(mappedBy = "order")
    @JsonManagedReference(value="order")
    //@JsonIgnore
    private List<OrderDetails> orderDetails = new ArrayList<OrderDetails>();

    public Orders() {
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

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public List<Planner> getPlanners() {
        return this.planners;
    }

    public List<Logo> getLogos() {
        return this.logos;
    }

    public void setPlanners(List<Planner> planners) {
        this.planners = planners;
    }

    public void setLogos(List<Logo> logos) {
        this.logos = logos;
    }

    public void setOnePlanner(Planner planner) {
        this.planners.add(planner);
    }

    public void setOneLogo(Logo logo) {
        this.logos.add(logo);
    }

    public String getEmail() {
        return this.email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
    public void setOrderDetails(OrderDetails details) {
        this.orderDetails.add(details);
    }
    public List<OrderDetails> getOrderDetails() {
        return this.orderDetails;
    }
    public void setCoupon(String coupon) {
        this.couponName = coupon;
    }
    public String getCoupon() {
        return this.couponName;
    }
}