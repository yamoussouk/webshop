package com.example.backend.service;

import java.nio.charset.StandardCharsets;

import javax.mail.internet.MimeMessage;

import com.example.backend.model.Mail;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring5.SpringTemplateEngine;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender emailSender;

    @Autowired
    private SpringTemplateEngine templateEngine;

    public void sendEmail(Mail mail, String template) {
        try {
            MimeMessage message = emailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message,
                    MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED,
                    StandardCharsets.UTF_8.name());

            Context context = new Context();
            context.setVariables(mail.getModel());
            String html = "";
            if (template.equals("reset")) {
                html = templateEngine.process("email/email-template", context);
            } else if (template.equals("simple")) {
                html = templateEngine.process("email/simple-email-template", context);
            } else if (template.equals("order-success")) {
                html = templateEngine.process("email/order-success-email-template", context);
            } else {
                html = templateEngine.process("email/activation-email-template", context);
            }

            helper.setTo(mail.getTo());
            helper.setText(html, true);
            helper.setSubject(mail.getSubject());
            helper.setFrom(mail.getFrom());


            emailSender.send(message);
        } catch (Exception e){
            throw new RuntimeException(e);
        }
    }

}