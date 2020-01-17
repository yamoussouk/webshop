package com.example.backend.dto;

import java.util.ArrayList;
import java.util.List;

import com.example.backend.model.Logo;
import com.example.backend.model.Planner;

public class TaskDetailsDto {
    private Long from;
    private Long to;
    private double percent;
    private List<Planner> planners = new ArrayList<>();
    List<Logo> logos = new ArrayList<>();

    public void setFrom(Long from) {
        this.from = from;
    }
    public void setTo(Long to) {
        this.to = to;
    }
    public void setPercent(double percent) {
        this.percent = percent;
    }
    public void setPlanners(List<Planner> planners) {
        this.planners = planners;
    }
    public void setLogos(List<Logo> logos) {
        this.logos = logos;
    }
    public Long getFrom() {
        return this.from;
    }
    public Long getTo() {
        return this.to;
    }
    public double getPercent() {
        return this.percent;
    }
    public List<Planner> getPlanners() {
        return this.planners;
    }
    public List<Logo> getLogos() {
        return this.logos;
    }
}