package com.example.backend.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.NoSuchElementException;
import java.util.Optional;

import com.example.backend.exception.NotFoundException;
import com.example.backend.model.Coupon;
import com.example.backend.repository.CouponRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class CouponServiceImpl implements CouponService {

    @Autowired
    private CouponRepository couponRepository;

    public CouponServiceImpl() {}

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
    public List<Coupon> getAll() {
        List<Coupon> allCoupons = new ArrayList<>();
        this.couponRepository.findAll().iterator().forEachRemaining(allCoupons::add);
        // if(enabled) {
        //     return allCoupons.stream().filter(coupon -> coupon.getEnabled() == 1).collect(Collectors.toList());
        // } else {
        //     return allCoupons;
        // }
        return allCoupons;
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

    @Override
    public Coupon saveCoupon(Coupon c) {
        return this.couponRepository.save(c);
    }

    @Override
    public void enableCoupon(final String id) {
        final Coupon c = getCouponById(new Long(id));
        final int en = c.getEnabled();
        if (en == 0) {
            c.setEnabled(1);
        } else {
            c.setEnabled(0);
        }
        saveCoupon(c);
    }

    @Override
    public Map<String, Map<String, String>> mergeCoupons(final List<Coupon> coupons, final Map<String, Map<String, String>> tasks) {
        Map<String, Map<String, String>> allTogether = new HashMap<>();
        int keyCounter = 0;
        try {
            boolean isIn = false;
            for (final Coupon coupon : coupons) {
                String couponId = String.valueOf(coupon.getId());
                Iterator<Entry<String, Map<String, String>>> it = tasks.entrySet().iterator();
                String foundKey = "";
                while (it.hasNext()) {
                    Map.Entry pair = (Map.Entry)it.next();
                    Object t = ((Map) pair.getValue()).get("id");
                    if (t.equals(couponId)) {
                        isIn = true;
                        foundKey = pair.getKey().toString();
                    }
                }
                Map<String, String> temp = new HashMap<>();
                temp.put("id", String.valueOf(coupon.getId()));
                temp.put("name", coupon.getName());
                temp.put("percent", String.valueOf(coupon.getPercent()));
                temp.put("enabled", String.valueOf(coupon.getEnabled()));
                if (!isIn) {
                    temp.put("from", "");
                    temp.put("to", "");
                } else {
                    temp.put("from", tasks.get(foundKey).get("from"));
                    temp.put("to", tasks.get(foundKey).get("to"));
                }
                allTogether.put(String.valueOf(keyCounter), temp);
                keyCounter++;
                isIn = false;
            }
            return allTogether;
        } catch (NoSuchElementException e) {
            Map<String, Map<String, String>> converted = new HashMap<>();
            int counter = 0;
            for (Coupon c : coupons) {
                Map<String, String> temp = new HashMap<>();
                temp.put("id", String.valueOf(c.getId()));
                temp.put("name", c.getName());
                temp.put("percent", String.valueOf(c.getPercent()));
                temp.put("enabled", String.valueOf(c.getEnabled()));
                temp.put("from", "");
                temp.put("to", "");
                converted.put(String.valueOf(counter), temp);
                counter++;
            }
            return converted;
        }
    }
}