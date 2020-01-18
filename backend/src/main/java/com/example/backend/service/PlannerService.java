package com.example.backend.service;

import com.example.backend.command.PlannerCommand;
import com.example.backend.model.Planner;

import java.util.List;

public interface PlannerService {

    List<Planner> getPlanners(boolean enabled);
    Planner findById(Long id);
    PlannerCommand findCommandById(Long id);
    void deleteById(Long idToDelete);
    Planner savePlanner(Planner product);
}
