package com.example.backend.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.stream.Collectors;

import com.example.backend.exception.NotFoundException;
import com.example.backend.model.Discount;
import com.example.backend.model.Product;
import com.example.backend.repository.DiscountRepository;
import com.example.backend.repository.ProductRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class DiscountServiceImpl implements DiscountService {

    @Autowired
    private DiscountRepository discountRepository;
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private ProductService productService;

    public DiscountServiceImpl() {}

    @Override
    public ProductService getProductService () {
        return this.productService;
    }

    @Override
    public void addDiscount(Discount discount) {
        this.discountRepository.save(discount);
    }

    @Override
    public Discount getDiscountById(Long id) {
        Optional<Discount> discount = this.discountRepository.findById(id);
        if (discount.isPresent()) {
            return discount.get();
        } else {
            throw new NotFoundException("discount with given id has not found!");
        }
    }

    @Override
    public void deleteDiscountById(Long id) {
        this.discountRepository.deleteById(id);
    }

    @Override
    public void changeEnabledDiscountById(Long id) {
        Discount discount = getDiscountById(id);
        int enabled = discount.getEnabled() == 0 ? 1 : 0;
        discount.setEnabled(enabled);
        this.discountRepository.save(discount);
    }

    @Override
    public void modifyDiscount(Discount discount) {
        this.discountRepository.save(discount);
    }

    @Override
    public List<Discount> getAll() {
        List<Discount> allDiscounts = new ArrayList<>();
        this.discountRepository.findAll().iterator().forEachRemaining(allDiscounts::add);
        // if(enabled) {
        //     return alldiscounts.stream().filter(discount -> discount.getEnabled() == 1).collect(Collectors.toList());
        // } else {
        //     return alldiscounts;
        // }
        return allDiscounts;
    }

    @Override
    public Discount getDiscountByName(String name) {
        Optional<Discount> discount = this.discountRepository.findByName(name);
        if (discount.isPresent()) {
            return discount.get();
        } else {
            return null;
        }
    }

    @Override
    public Discount saveDiscount(Discount c) {
        return this.discountRepository.save(c);
    }

    @Override
    public void setDiscountOnProducts(List<Product> products, int percent, boolean zero) {
        for (Product p : products) {
            int value = zero ? 0 : percent;
            p.setDiscount(value);
            productRepository.save(p);
        }
    }

    @Override
    public Map<String, Map<String, String>> mergeDiscounts(final List<Discount> discounts, final Map<String, Map<String, String>> tasks) {
        Map<String, Map<String, String>> allTogether = new HashMap<>();
        int keyCounter = 0;
        try {
            boolean isIn = false;
            for (final Discount discount : discounts) {
                String discountId = String.valueOf(discount.getId());
                Iterator<Entry<String, Map<String, String>>> it = tasks.entrySet().iterator();
                String foundKey = "";
                while (it.hasNext()) {
                    Map.Entry pair = (Map.Entry)it.next();
                    Object t = ((Map) pair.getValue()).get("id");
                    if (t.equals(discountId)) {
                        isIn = true;
                        foundKey = pair.getKey().toString();
                    }
                }
                Map<String, String> temp = new HashMap<>();
                temp.put("id", String.valueOf(discount.getId()));
                temp.put("name", discount.getName());
                temp.put("percent", String.valueOf(discount.getPercent()));
                temp.put("enabled", String.valueOf(discount.getEnabled()));
                temp.put("productNames", discount.getProducts().stream().map(p -> productService.getProduct(p).getName()).collect(Collectors.joining(", ")));
                temp.put("productIds", discount.getProducts().stream().map(p -> String.valueOf(p)).collect(Collectors.joining(", ")));
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
            for (Discount c : discounts) {
                Map<String, String> temp = new HashMap<>();
                temp.put("id", String.valueOf(c.getId()));
                temp.put("name", c.getName());
                temp.put("percent", String.valueOf(c.getPercent()));
                temp.put("enabled", String.valueOf(c.getEnabled()));
                temp.put("productNames", c.getProducts().stream().map(p -> productService.getProduct(p).getName()).collect(Collectors.joining(", ")));
                temp.put("productIds", c.getProducts().stream().map(p -> String.valueOf(p)).collect(Collectors.joining(", ")));
                temp.put("from", "");
                temp.put("to", "");
                converted.put(String.valueOf(counter), temp);
                counter++;
            }
            return converted;
        }
    }

    @Override
    public void enableDiscount(final String id) {
        final Discount d = getDiscountById(new Long(id));
        final int en = d.getEnabled();
        if (en == 0) {
            d.setEnabled(1);
            List<Product> ps = d.getProducts().stream().map(prod_id -> productService.getProduct(prod_id)).collect(Collectors.toList());
            setDiscountOnProducts(ps, d.getPercent(), true);
        } else {
            d.setEnabled(0);
            List<Product> ps = d.getProducts().stream().map(prod_id -> productService.getProduct(prod_id)).collect(Collectors.toList());
            setDiscountOnProducts(ps, d.getPercent(), false);
        }
        saveDiscount(d);
    }
}