package com.example.backend.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;


@Entity
public class Coupon {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private int percent;
    private int enabled;

    public Coupon () {}

    public Coupon(String name, int percent, int enabled) {
        this.name = name;
        this.percent = percent;
        this.enabled = enabled;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPercent(int percent) {
        this.percent = percent;
    }

    public void setEnabled (int enabled) {
        this.enabled = enabled;
    }

    public Long getId() {
        return this.id;
    }

    public String getName () {
        return this.name;
    }

    public int getPercent () {
        return this.percent;
    }

    public int getEnabled () {
        return this.enabled;
    }
}