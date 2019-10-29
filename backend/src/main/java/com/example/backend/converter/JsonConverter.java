package com.example.backend.converter;

import com.example.backend.command.PlannerCommand;
import com.example.backend.command.LogoCommand;
import com.example.backend.model.Orders;
import com.example.backend.model.Planner;
import com.example.backend.model.Logo;
import com.example.backend.model.Product;
import com.example.backend.repository.ProductRepository;
import com.example.backend.repository.UserRepository;
import org.json.simple.JSONArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.*;

import org.json.simple.JSONObject;

@Service
public class JsonConverter {

    private static UserRepository userRepository;
    private static UserToUserCommand userConverter;
    private static ProductRepository productRepository;
    private static PlannerToPlannerCommand plannerConverter;
    private static LogoToLogoCommand logoConverter;

    @Autowired
    public JsonConverter(UserRepository userRepository, 
    UserToUserCommand userConverter, 
    ProductRepository productRepository, 
    PlannerToPlannerCommand plannerConverter, 
    LogoToLogoCommand logoToLogoCommand) {
        JsonConverter.userRepository = userRepository;
        JsonConverter.userConverter = userConverter;
        JsonConverter.productRepository = productRepository;
        JsonConverter.plannerConverter = plannerConverter;
        JsonConverter.logoConverter = logoConverter;
    }

    public static Orders convertJsonToOrder(Map<String, Object> object) {
        if (object == null) {
            return null;
        }

        Orders order = new Orders();
        //String id = String.valueOf(object.get("id"));
        String price = String.valueOf(object.get("price"));
        String purchasedTime = String.valueOf(object.get("ptime"));
        SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = new Date(Long.parseLong(purchasedTime));
        String userId = String.valueOf(object.get("uid"));
        List<Integer> jsonProducts = (List<Integer>) object.get("products");
        //order.setId(Long.valueOf(id));
        order.setPrice(Double.valueOf(price));
        order.setPurchaseTime(date);
        order.setUser(userRepository.findById(Long.valueOf(userId)).get());
        for (int i=0; i < jsonProducts.size(); i++) {
            Product product = productRepository.findById(Long.valueOf(jsonProducts.get(i).toString())).get();
            order.setOneProduct(product);
        }
        return order;
    }

    public static Map<String, Object> convertOrderToJson(Orders order) {
        if (order == null) {
            return null;
        }

        Map<String, Object> json = new HashMap<>();
        json.put("id", order.getId());
        json.put("price", order.getPrice());
        json.put("ptime", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(order.getPurchaseTime()));
        json.put("uid", order.getUser().getId());
        JSONArray productIds = new JSONArray();
        List<PlannerCommand> pids = new ArrayList<>();
        List<LogoCommand> lids = new ArrayList<>();
        order.getProducts().forEach(product -> {
            if (product.getType().equals("Planner")) {
                pids.add(plannerConverter.convert((Planner) product));
            } else {
                lids.add(logoConverter.convert((Logo) product));
            }
        });
        productIds.addAll(pids);
        productIds.addAll(lids);
        json.put("products", productIds);
        return json;
    }
}

