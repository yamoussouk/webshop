package com.example.backend.repository;

import com.example.backend.model.Orders;
import com.example.backend.model.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrdersRepository extends CrudRepository<Orders, Long> {

    List<Orders> findByUser(User user);
    @Override
    List<Orders> findAll();
}