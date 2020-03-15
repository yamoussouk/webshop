package com.example.backend.service;

import java.util.List;
import java.util.Map;

import com.example.backend.model.Coupon;

public interface CouponService {

    List<Coupon> getAll();
    void addCoupon(Coupon coupon);
    Coupon getCouponById(Long id);
    Coupon getCouponByName(String name);
    void deleteCouponById(Long id);
    void changeEnabledCouponById(Long id);
    void modifyCoupon(Coupon coupon);
    Coupon saveCoupon(Coupon coupon);
    Map<String, Map<String, String>> mergeCoupons(final List<Coupon> coupons, final Map<String, Map<String, String>> tasks);
    void enableCoupon(final String id);
}