package com.example.backend.repository;

import com.example.backend.model.Logo;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LogoRepository extends CrudRepository<Logo, Long> {
    
}