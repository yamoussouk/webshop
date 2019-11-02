package com.example.backend.model;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.JoinTable;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class Planner extends Product {

    public enum Size {
        A4,
        A5,
        USLETTER,
        PERSONAL,
        HALFSIZE
    }

    public enum StartingDay {
        SUNDAY,
        MONDAY
    }
    
    @JsonIgnore
    @Enumerated(EnumType.STRING)
    private Size size;

    @JsonIgnore
    @Enumerated(EnumType.STRING)
    private StartingDay startingDay; 

    private String downloadLink;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "order_planners",
            joinColumns = @JoinColumn(
                    name = "planner_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(
                    name = "order_id", referencedColumnName = "id"))
    private Set<Orders> orders = new HashSet<>();

    public Planner () {
        this.setType("Planner"); 
    }

    public String getSize() {
        return this.size.name();
    }

    public void setSize(String size) {
        this.size = Size.valueOf(size);
    }

    public String getStartingDay() {
        return this.startingDay.name();
    }

    public void setStartingDay(String day) {
        this.startingDay = StartingDay.valueOf(day);
    }

    public void setDownloadLink (String link) {
        this.downloadLink = link;
    }

    public String getDownloadLink () {
        return this.downloadLink;
    }
}