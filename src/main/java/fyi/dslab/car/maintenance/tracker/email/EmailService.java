package fyi.dslab.car.maintenance.tracker.email;

import lombok.RequiredArgsConstructor;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EmailService {

    private final MailSender mailSender;

    private final EmailProperties emailProperties;

    //@Async
    public void send(String email, long authCode) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(emailProperties.getFrom());
        message.setTo(email);
        message.setSubject("Login request to Car Maintenance Tracker");
        message.setText("Your auth code is: %d".formatted(authCode));
        mailSender.send(message);
    }
}
