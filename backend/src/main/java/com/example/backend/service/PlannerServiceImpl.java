package com.example.backend.service;

import com.example.backend.command.PlannerCommand;
import com.example.backend.converter.PlannerToPlannerCommand;
import com.example.backend.exception.NotFoundException;
import com.example.backend.model.Planner;
import com.example.backend.repository.PlannerRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Service
public class PlannerServiceImpl implements PlannerService {

    private final PlannerRepository plannerRepository;
    private final PlannerToPlannerCommand converter;

    public PlannerServiceImpl(PlannerRepository plannerRepository, PlannerToPlannerCommand converter) {
        this.plannerRepository = plannerRepository;
        this.converter = converter;
    }

    @Override
    public List<Planner> getPlanners(boolean enabled) {
        List<Planner> allPlanners = new ArrayList<>();
        this.plannerRepository.findAll().iterator().forEachRemaining(allPlanners::add);
        if(enabled) {
            return allPlanners.stream().filter(product -> product.getEnabled() == 1).collect(Collectors.toList());
        } else {
            return allPlanners;
        }
    }

    @Override
    public Planner findById(Long id) {
        Optional<Planner> planner = this.plannerRepository.findById(id);
        if (planner.isPresent()) {
            return planner.get();
        } else {
            throw new NotFoundException("Product with given id has not found!");
        }
    }

    @Override
    @Transactional
    public PlannerCommand findCommandById(Long id) {
        return converter.convert(findById(id));
    }

    @Override
    @Transactional
    public void deleteById(Long idToDelete) {
        plannerRepository.deleteById(idToDelete);
    }

    @Override
    public Planner savePlanner(Planner product) {
        return plannerRepository.save(product);
    }
}

