package com.rawajsupermarket.service.impl;

import com.rawajsupermarket.exception.FeatureNetworkUnavailableException;
import com.rawajsupermarket.exception.LocalizedException;
import com.rawajsupermarket.service.EmailService;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpStatus;
import org.springframework.mail.MailAuthenticationException;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
@RequiredArgsConstructor
@Slf4j
public class EmailServiceImpl implements EmailService {

    private final JavaMailSender mailSender;

    @Value("${mail.from-address:}")
    private String fromAddress;

    @Override
    public void sendPlainTextEmail(String to, String subject, String body) {
        if (fromAddress == null || fromAddress.isBlank()) {
            // Fails closed with a clear message instead of letting JavaMailSender throw
            // an opaque authentication error when spring.mail.username was never set.
            throw new RuntimeException("Email sending is not configured for this store - contact your administrator");
        }

        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(fromAddress);
        message.setTo(to);
        message.setSubject(subject);
        message.setText(body);

        try {
            mailSender.send(message);
            log.info("Email sent to {} - subject: {}", to, subject);
        } catch (MailAuthenticationException e) {
            // Bad SMTP credentials, not a connectivity problem - don't tell an
            // admin "check your internet" when the real issue is a wrong password.
            log.error("Mail server rejected credentials sending to {}: {}", to, e.getMessage());
            throw new LocalizedException(HttpStatus.BAD_GATEWAY, "EMAIL_SEND_FAILED", "Failed to send email: " + e.getMessage(), e);
        } catch (MailException e) {
            // Anything else (connection refused/timeout/unreachable host) is the
            // "no internet" case.
            log.error("Could not reach mail server sending to {}: {}", to, e.getMessage());
            throw new FeatureNetworkUnavailableException("FEATURE_NETWORK_UNAVAILABLE_EMAIL",
                    "Could not reach the mail server - check the store's internet connection", e);
        }
    }

    @Override
    public void sendEmailWithAttachment(String to, String subject, String body,
                                         byte[] attachment, String attachmentFilename, String attachmentContentType) {
        if (fromAddress == null || fromAddress.isBlank()) {
            throw new RuntimeException("Email sending is not configured for this store - contact your administrator");
        }

        try {
            MimeMessage message = mailSender.createMimeMessage();
            // true = multipart, required for addAttachment to work
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            helper.setFrom(fromAddress);
            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(body);
            helper.addAttachment(attachmentFilename, new ByteArrayResource(attachment), attachmentContentType);

            mailSender.send(message);
            log.info("Email with attachment sent to {} - subject: {}", to, subject);
        } catch (MailAuthenticationException e) {
            log.error("Mail server rejected credentials sending attachment to {}: {}", to, e.getMessage());
            throw new LocalizedException(HttpStatus.BAD_GATEWAY, "EMAIL_SEND_FAILED", "Failed to send email: " + e.getMessage(), e);
        } catch (jakarta.mail.MessagingException | MailException e) {
            log.error("Could not reach mail server sending attachment to {}: {}", to, e.getMessage());
            throw new FeatureNetworkUnavailableException("FEATURE_NETWORK_UNAVAILABLE_EMAIL",
                    "Could not reach the mail server - check the store's internet connection", e);
        }
    }
}
