package com.example.backend.repository;

import com.example.backend.model.OrderDetails;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderDetailsRepository extends CrudRepository<OrderDetails, Long> {
    
}