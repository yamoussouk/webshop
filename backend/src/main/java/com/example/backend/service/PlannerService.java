package com.example.backend.service;

import com.example.backend.command.PlannerCommand;
import com.example.backend.model.Planner;

import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface PlannerService {

    List<Planner> getPlanners(boolean enabled);
    Planner findById(Long id);
    PlannerCommand findCommandById(Long id);
    void deleteById(Long idToDelete);
    Planner savePlanner(Planner product);
    Planner findByName(String name);
    void savePlannerPreparator(final String product, final MultipartFile[] file);
    void enablePlanner(final String id);
    PlannerCommand updatePlannerPreparator(final String product, final List<String> removed, final MultipartFile[] files);
}
