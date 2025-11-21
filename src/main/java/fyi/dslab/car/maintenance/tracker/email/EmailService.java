package fyi.dslab.car.maintenance.tracker.email;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class EmailService {

    private static final String AUTH_TEMPLATE = "login-auth-code.ftl";

    private final JavaMailSender mailSender;

    private final EmailProperties emailProperties;

    private final Configuration freemarkerConfig;

    @Async
    public void send(String email, long authCode) {
        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message,
                    StandardCharsets.UTF_8.name());
            helper.setFrom(emailProperties.getFrom());
            helper.setTo(email);
            helper.setSubject("Login request to Car Maintenance Tracker");
            helper.setText(renderAuthCodeTemplate(authCode), true);
            mailSender.send(message);
        } catch (MessagingException ex) {
            throw new IllegalStateException("Failed to send auth code email to '" + email + "'.",
                    ex);
        }
    }

    private String renderAuthCodeTemplate(long authCode) {
        try {
            Template template = freemarkerConfig.getTemplate(AUTH_TEMPLATE);
            Map<String, Object> model = Map.of("authCode",
                    String.valueOf(authCode),
                    "appName",
                    "Car Maintenance Tracker");
            return FreeMarkerTemplateUtils.processTemplateIntoString(template, model);
        } catch (IOException | TemplateException ex) {
            throw new IllegalStateException("Failed to render auth code email template", ex);
        }
    }
}
