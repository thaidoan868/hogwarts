package vn.conguyetduong.hogwarts.business.service.external;

import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;

@Slf4j
@Service
@RequiredArgsConstructor
public class MailService {
    @Value("${spring.mail.username}")
    private String fromEmail;
    @Value("${spring.mail.from-display-name}")
    private String fromDisplayName;
    private final JavaMailSender mailSender;


    public void sendMail(String toEmail, String subject, String content) {
        try {
            // create email body
            MimeMessage mimeMessage = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, false, StandardCharsets.UTF_8.name());
            helper.setTo(toEmail);
            helper.setFrom(fromEmail, fromDisplayName);
            helper.setSubject(subject);
            helper.setText(content, false);

            mailSender.send(mimeMessage);
            log.info("Email sent to {}", toEmail);

        } catch (Exception e) {
            log.error("Failed to send email to %s".formatted(toEmail), e);
            throw new RuntimeException("Failed to send email", e);
        }
    }
}
