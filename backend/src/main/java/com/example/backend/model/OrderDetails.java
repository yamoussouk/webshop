package com.example.backend.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import com.fasterxml.jackson.annotation.JsonBackReference;

@Entity
public class OrderDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long productID;
    private String logoText;
    private String startingDay;
    private String size;

    @ManyToOne
    @JsonBackReference
    private Orders order;

    public OrderDetails () {

    }

    public Long getId() {
        return this.id;
    }
    public Long getProductID() {
        return this.productID;
    }
    public String getLogoText() {
        return this.logoText;
    }
    public String getStartingDay() {
        return this.startingDay;
    }
    public String getSize() {
        return this.size;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public void setProductID(Long id) {
        this.productID = id;
    }
    public void setLogoText(String text) {
        this.logoText = text;
    }
    public void setStartingDay(String day) {
        this.startingDay = day;
    }
    public void setSize(String size) {
        this.size = size;
    }
    public void setOrders(Orders order) {
        this.order = order;
    }
    public Orders getOrder() {
        return this.order;
    }
}