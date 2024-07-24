package com.capstone.closetconnect.services.email;

import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import java.util.HashMap;
import java.util.Map;

@Service
@Slf4j
@RequiredArgsConstructor
public class EmailServiceImpl implements  EmailService{

    private final JavaMailSender mailSender;

    private final TemplateEngine templateEngine;

    @Override
    public void sendEmail(String to, String subject, String templateName, Map<String, Object> variables) {
        Context context = new Context();
        context.setVariables(variables);
        String body = templateEngine.process(templateName, context);

        MimeMessage mimeMessage = mailSender.createMimeMessage();
        try {
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);
            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(body, true); // true indicates HTML

            mailSender.send(mimeMessage);
        } catch (Exception e) {
            log.error("Failed to send email", e);
        }
    }

    @Override
    public Map<String, String> formEmailBody(String name, String content, String subject, String templateName) {
        Map<String, String> emailVariables = new HashMap<>();
        emailVariables.put("name", name);
        emailVariables.put("content", content);
        emailVariables.put("subject", subject);
        emailVariables.put("templateName", templateName);
        return emailVariables;
    }

    @Override
    public Map<String, Object> formTemplateVariables(String name, String content) {
        Map<String, Object> templateVariables = new HashMap<>();
        templateVariables.put("name", name);
        templateVariables.put("content", content);
        return templateVariables;
    }
}
