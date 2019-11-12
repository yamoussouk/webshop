package com.example.backend.repository;

import java.util.Optional;

import com.example.backend.model.Coupon;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CouponRepository extends CrudRepository<Coupon, Long> {
    Optional<Coupon> findByName(String name);
}