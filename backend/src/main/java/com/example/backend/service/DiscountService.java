package com.example.backend.service;

import java.util.List;
import java.util.Map;

import com.example.backend.model.Discount;
import com.example.backend.model.Product;

public interface DiscountService {

    List<Discount> getAll();
    void addDiscount(Discount discount);
    Discount getDiscountById(Long id);
    Discount getDiscountByName(String name);
    void deleteDiscountById(Long id);
    void changeEnabledDiscountById(Long id);
    void modifyDiscount(Discount coupon);
    Discount saveDiscount(Discount discount);
    void setDiscountOnProducts(List<Product> products, int percent, boolean zero);
    Map<String, Map<String, String>> mergeDiscounts(final List<Discount> discounts, final Map<String, Map<String, String>> tasks);
    void enableDiscount(final String id, final String type);
}