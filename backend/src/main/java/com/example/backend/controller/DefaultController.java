package com.example.backend.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.example.backend.command.PlannerCommand;
import com.example.backend.command.LogoCommand;
import com.example.backend.model.Coupon;
import com.example.backend.model.Mail;
import com.example.backend.model.Planner;
import com.example.backend.model.Logo;
import com.example.backend.service.CouponService;
import com.example.backend.service.EmailService;
import com.example.backend.service.PlannerService;
import com.example.backend.service.LogoService;
import com.example.backend.converter.PlannerToPlannerCommand;
import com.example.backend.converter.LogoToLogoCommand;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.beans.factory.annotation.Value;

@RestController
public class DefaultController {

    private final PlannerService plannerService;
    private final LogoService logoService;
    private final PlannerToPlannerCommand PlannerToPlannerCommand = new PlannerToPlannerCommand();
    private final LogoToLogoCommand logoToLogoCommand = new LogoToLogoCommand();
    private final EmailService emailService = new EmailService();
    private final CouponService couponService;

    private final String[] europeanUnion = {"BE", "BG", "CZ", "DE", "DK", "EE", "IE", "EL", "ES", "FR", 
    "HR", "IT", "CY", "LV", "LT", "LU", "HU", "MT", "NL", "AT", "PL", "PT", "RO", "SI", "SK", "FI", "SE", "UK"};

    @Value("${owner.email}")
    private String ownerEmail;

    public DefaultController (PlannerService plannerService, LogoService logoService, CouponService couponService) {
        this.plannerService = plannerService;
        this.logoService = logoService;
        this.couponService = couponService;
    }

    @GetMapping("/default/planners/all/{country}")
    public List<PlannerCommand> getAllPlanners(@PathVariable(name = "country") String country) {
        List<Planner> products =  this.plannerService.getPlanners(true);
        List<PlannerCommand> returnedValue = new ArrayList<PlannerCommand>();
        for (Planner p : products) {
            returnedValue.add(setPlannerVAT(PlannerToPlannerCommand.convert(p), country));
        }
        return returnedValue;
    }

    @GetMapping("/default/logos/all/{country}")
    public List<LogoCommand> getAllLogos(@PathVariable(name = "country") String country) {
        List<Logo> products =  this.logoService.getLogos(true);
        List<LogoCommand> returnedValue = new ArrayList<LogoCommand>();
        for (Logo p : products) {
            returnedValue.add(setLogoVAT(logoToLogoCommand.convert(p), country));
        }
        return returnedValue;
    }

    @GetMapping("/default/planner/{id}/{country}")
    public PlannerCommand getPlannerById(@PathVariable(name = "id") String id,
     @PathVariable(name = "country") String country) {
        Planner p = plannerService.findById(new Long(id));
        return setPlannerVAT(PlannerToPlannerCommand.convert(p), country);
    }

    @GetMapping("/default/logo/{id}/{country}")
    public LogoCommand getLogoById(@PathVariable(name = "id") String id,
     @PathVariable(name = "country") String country) {
        Logo p = logoService.findById(new Long(id));
        return setLogoVAT(logoToLogoCommand.convert(p), country);
    }

    @PostMapping("/default/contact")
    public ResponseEntity<?> contact(@RequestParam("from") String from, @RequestParam("text") String email) {
        this.emailService.sendEmail(mailAssembler(from, email), "contact");
        return new ResponseEntity<>("OK", HttpStatus.OK);
    }

    @GetMapping("/default/coupon/{coupon}")
    public  ResponseEntity<?> validateCoupon(@PathVariable(name = "coupon") String coupon) {
        Coupon c = this.couponService.getCouponByName(coupon);
        if (c != null) {
            return ResponseEntity.ok(c.getPercent());
        } else {
            return ResponseEntity.ok("No coupon found!");
        }
    }

    private Mail mailAssembler(String from, String email) {
        Mail contactMessage = new Mail();
        contactMessage.setFrom("valaki@gmail.com");
        contactMessage.setTo(ownerEmail);
        contactMessage.setSubject("Contact message from the webshop");
        Map<String, Object> model = new HashMap<>();
        model.put("message", email);
        model.put("from", from);
        contactMessage.setModel(model);
        return contactMessage;
    }

    private PlannerCommand setPlannerVAT(PlannerCommand command, String country) {
        if (Arrays.stream(europeanUnion).anyMatch(country::equals)) {
            double vat = command.getPrice() * 0.27;
            command.setVat(vat);
        }
        return command;
    }

    private LogoCommand setLogoVAT(LogoCommand command, String country) {
        if (Arrays.stream(europeanUnion).anyMatch(country::equals)) {
            double vat = command.getPrice() * 0.27;
            command.setVat(vat);
        }
        return command;
    }
}