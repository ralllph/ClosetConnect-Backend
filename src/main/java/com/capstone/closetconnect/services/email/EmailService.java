package com.capstone.closetconnect.services.email;

import java.util.Map;

public interface EmailService {

    void sendEmail(String to, String subject, String templateName, Map<String, Object> variables);

    Map<String,String> formEmailBody(String name, String content, String subject,
                                     String templateName);

    Map<String,Object> formTemplateVariables(String name, String content);
}
