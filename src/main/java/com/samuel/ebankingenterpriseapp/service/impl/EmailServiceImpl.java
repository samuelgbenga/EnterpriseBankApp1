package com.samuel.ebankingenterpriseapp.service.impl;

import com.samuel.ebankingenterpriseapp.model.EmailDto;
import com.samuel.ebankingenterpriseapp.service.EmailService;
import org.springframework.core.io.ClassPathResource;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.spring6.SpringTemplateEngine;

import org.thymeleaf.context.Context;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Map;


@Service
@RequiredArgsConstructor
public class EmailServiceImpl implements EmailService {


    @Value("${spring.mail.username}")
    private String senderEmail;

    private final JavaMailSender javaMailSender;
    private final SpringTemplateEngine tEngine;

    @Override
    public void registrationMail(EmailDto emailDto) throws MessagingException, IOException {

        MimeMessage msg = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(msg, MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED, StandardCharsets.UTF_8.name());

        Context context = new Context();
        Map<String, Object> variables = Map.of(
                "name", emailDto.getEvent(),
                "event", emailDto.getEvent()
        );
        context.setVariables(variables);
        helper.setFrom(senderEmail);
        helper.setTo(emailDto.getRecipient());
        helper.setSubject(emailDto.getSubject());
        String html = tEngine.process("RegistrationEmail", context);
        helper.setText(html, true);

        // Add an attachment
//        File attachment = new File("/path/to/your/attachment.pdf"); // Specify the file path
//        if (attachment.exists()) {
//            helper.addAttachment("attachment.pdf", attachment); // Name the attachment as it should appear in the email
//        }

        // Load the file from the resources folder
        // Todo: will be used later
        Resource resource = new ClassPathResource("static/attachimg.png"); // Path relative to src/main/resources
        if (resource.exists()) {
            File file = resource.getFile();
            helper.addInline("HRImage", file); // Name the attachment as it should appear in the email
        }

        javaMailSender.send(msg);

    }


}
