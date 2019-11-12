package com.example.backend.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import com.example.backend.model.Coupon;
import com.example.backend.repository.CouponRepository;
import com.example.backend.exception.NotFoundException;

import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class CouponServiceImpl implements CouponService {

    private final CouponRepository couponRepository;

    public CouponServiceImpl(CouponRepository couponRepository) {
        this.couponRepository = couponRepository;
    }

    @Override
    public void addCoupon(Coupon coupon) {
        this.couponRepository.save(coupon);
    }

    @Override
    public Coupon getCouponById(Long id) {
        Optional<Coupon> coupon = this.couponRepository.findById(id);
        if (coupon.isPresent()) {
            return coupon.get();
        } else {
            throw new NotFoundException("Coupon with given id has not found!");
        }
    }

    @Override
    public void deleteCouponById(Long id) {
        this.couponRepository.deleteById(id);
    }

    @Override
    public void changeEnabledCouponById(Long id) {
        Coupon coupon = getCouponById(id);
        int enabled = coupon.getEnabled() == 0 ? 1 : 0;
        coupon.setEnabled(enabled);
        this.couponRepository.save(coupon);
    }

    @Override
    public void modifyCoupon(Coupon coupon) {
        this.couponRepository.save(coupon);
    }

    @Override
    public List<Coupon> getAll(boolean enabled) {
        List<Coupon> allCoupons = new ArrayList<>();
        this.couponRepository.findAll().iterator().forEachRemaining(allCoupons::add);
        if(enabled) {
            return allCoupons.stream().filter(coupon -> coupon.getEnabled() == 1).collect(Collectors.toList());
        } else {
            return allCoupons;
        }
    }

    @Override
    public Coupon getCouponByName(String name) {
        Optional<Coupon> coupon = this.couponRepository.findByName(name);
        if (coupon.isPresent()) {
            return coupon.get();
        } else {
            return null;
        }
    }
}