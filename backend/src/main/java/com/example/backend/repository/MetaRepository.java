package com.example.backend.repository;

import java.util.Optional;

import com.example.backend.model.Meta;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MetaRepository extends CrudRepository<Meta, Long> {

    Meta findByName(String name);
    Optional<Meta> findById(Long id);
}