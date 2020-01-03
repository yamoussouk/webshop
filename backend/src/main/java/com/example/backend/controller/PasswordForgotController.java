package com.example.backend.controller;

import com.example.backend.dto.PasswordForgotDto;
import com.example.backend.model.Mail;
import com.example.backend.model.PasswordResetToken;
import com.example.backend.model.User;
import com.example.backend.repository.PasswordResetTokenRepository;
import com.example.backend.service.EmailService;
import com.example.backend.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.*;

@Controller
public class PasswordForgotController {

    @Value("${frontend.port}")
    private String port;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Autowired private UserService userService;
    @Autowired private PasswordResetTokenRepository tokenRepository;
    @Autowired private EmailService emailService;

    @ModelAttribute("forgotPasswordForm")
    public PasswordForgotDto forgotPasswordDto() {
        return new PasswordForgotDto();
    }

    // @GetMapping
    // public String displayForgotPasswordPage() {
    //     return "forgot-password";
    // }

    @PostMapping(value= "/forgot_password")
    public ResponseEntity<?> processForgotPasswordForm(@RequestParam("forgotPasswordModel") String email, HttpServletRequest request) {

        User user = userService.findByEmail(email);
        if (user == null){
            return ResponseEntity.badRequest().body("No user with specific email!");
        }

        PasswordResetToken token = new PasswordResetToken();
        token.setToken(UUID.randomUUID().toString());
        token.setUser(user);
        token.setExpiryDate(30);
        tokenRepository.save(token);

        Mail mail = new Mail();
        mail.setFrom("tamas.kakuszi@gmail.com");
        mail.setTo(user.getEmail());
        mail.setSubject("Password reset request");

        Map<String, Object> model = new HashMap<>();
        model.put("token", token);
        model.put("user", user);
        model.put("signature", "https://testelek.com");
        String url = request.getScheme() + "://" + request.getServerName() + ":" + port;
        model.put("resetUrl", url + "/reset-password?token=" + token.getToken());
        mail.setModel(model);
        emailService.sendEmail(mail, "reset");

        return ResponseEntity.ok("Password reset email was sent!");
    }

    @PostMapping(value = "/reset-password")
    public ResponseEntity<?> processResetPassword(@RequestParam("new_password") String pass, @RequestParam("token") String token) {
        PasswordResetToken savedToken = tokenRepository.findByToken(token);
        if (savedToken == null) {
            return ResponseEntity.badRequest().body("Invalid token!");
        }
        if (savedToken.getExpiryDate().before(Calendar.getInstance().getTime())) {
            return ResponseEntity.badRequest().body("Token has expired!");
        }
        User user = savedToken.getUser();
        if (passwordEncoder.matches(pass, user.getPasswd())) {
            return ResponseEntity.badRequest().body("The new password must be different from the older one!");
        }
        user.setPasswd(passwordEncoder.encode(pass));
        user.setLastPasswordResetDate(new Date());
        userService.saveUser(user);

        Mail mail = new Mail();
        mail.setFrom("auto@test.hu");
        mail.setTo(user.getEmail());
        mail.setSubject("Password changed.");

        Map<String, Object> model = new HashMap<>();
        model.put("user", user);
        model.put("signature", "https://testelek.com");
        mail.setModel(model);
        emailService.sendEmail(mail, "simple");

        return ResponseEntity.ok("Password reset email was sent!");
    }

}

