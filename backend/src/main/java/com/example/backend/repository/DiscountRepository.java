package com.example.backend.repository;

import java.util.Optional;

import com.example.backend.model.Discount;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DiscountRepository extends CrudRepository<Discount, Long> {
    Optional<Discount> findByName(String name);
}