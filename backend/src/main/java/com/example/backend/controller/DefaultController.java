package com.example.backend.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.example.backend.command.PlannerCommand;
import com.example.backend.command.LogoCommand;
import com.example.backend.model.Image;
import com.example.backend.model.Mail;
import com.example.backend.model.Planner;
import com.example.backend.model.Logo;
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

    @Value("${owner.email}")
    private String ownerEmail;

    public DefaultController (PlannerService plannerService, LogoService logoService) {
        this.plannerService = plannerService;
        this.logoService = logoService;
    }

    @GetMapping("/default/planners/all")
    public List<PlannerCommand> getAllPlanners() {
        List<Planner> products =  this.plannerService.getPlanners(true);
        List<PlannerCommand> returnedValue = new ArrayList<PlannerCommand>();
        for (Planner p : products) {
            returnedValue.add(PlannerToPlannerCommand.convert(p));
        }
        return returnedValue;
    }

    @GetMapping("/default/logos/all")
    public List<LogoCommand> getAllLogos() {
        List<Logo> products =  this.logoService.getLogos(true);
        List<LogoCommand> returnedValue = new ArrayList<LogoCommand>();
        for (Logo p : products) {
            returnedValue.add(logoToLogoCommand.convert(p));
        }
        return returnedValue;
    }

    @GetMapping("/default/planner/{id}")
    public PlannerCommand getPlannerById(@PathVariable(name = "id") String id) {
        Planner p = plannerService.findById(new Long(id));
        return PlannerToPlannerCommand.convert(p);
    }

    @GetMapping("/default/logo/{id}")
    public LogoCommand getLogoById(@PathVariable(name = "id") String id) {
        Logo p = logoService.findById(new Long(id));
        return logoToLogoCommand.convert(p);
    }

    @GetMapping("/default/product/{id}/images")
    public Set<Image> getProductImages(@PathVariable(name = "id") String id) {
        return plannerService.findById(new Long(id)).getImages();
    }

    @PostMapping("/default/contact")
    public ResponseEntity<?> contact(@RequestParam("from") String from, @RequestParam("text") String email) {
        this.emailService.sendEmail(mailAssembler(from, email), "contact");
        return new ResponseEntity<>("OK", HttpStatus.OK);
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
}