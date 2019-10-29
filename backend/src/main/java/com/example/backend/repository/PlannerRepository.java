package com.example.backend.repository;

import com.example.backend.model.Planner;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PlannerRepository extends CrudRepository<Planner, Long> {
    
}