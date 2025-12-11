package vn.conguyetduong.hogwarts.business.service.external;

import io.opentelemetry.api.GlobalOpenTelemetry;
import io.opentelemetry.api.OpenTelemetry;
import io.opentelemetry.api.trace.Span;
import io.opentelemetry.api.trace.Tracer;
import io.opentelemetry.instrumentation.annotations.WithSpan;
import jakarta.annotation.PostConstruct;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
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
    private final Tracer tracer;


    public void sendMail(String toEmail, String subject, String content) {
        Span span = tracer.spanBuilder(this.getClass().getSimpleName()+ ".sendMail").startSpan();
        span.setAttribute("mail.to", toEmail);
        span.setAttribute("mail.subject", subject);
        try {
            // create email body
            MimeMessage mimeMessage = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, false, StandardCharsets.UTF_8.name());
            helper.setTo(toEmail);
            helper.setFrom(fromEmail, fromDisplayName);
            helper.setSubject(subject);
            helper.setText(content, false);

            mailSender.send(mimeMessage);
            span.setAttribute("email.status", "sent");
        } catch (Exception e) {
            span.setAttribute("email.status", "failed");
            span.recordException(e);
            throw new RuntimeException("Failed to send email", e);
        } finally {
            span.end();
        }
    }
}
