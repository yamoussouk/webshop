package com.example.backend.service;

import java.util.List;

import com.example.backend.model.Coupon;

public interface CouponService {

    List<Coupon> getAll(boolean enabled);
    void addCoupon(Coupon coupon);
    Coupon getCouponById(Long id);
    Coupon getCouponByName(String name);
    void deleteCouponById(Long id);
    void changeEnabledCouponById(Long id);
    void modifyCoupon(Coupon coupon);
}