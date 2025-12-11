package vn.conguyetduong.hogwarts.app.config;

import io.opentelemetry.api.OpenTelemetry;
import io.opentelemetry.api.trace.Tracer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenTelemetryConfig {
    @Bean
    public Tracer getTracer(OpenTelemetry openTelemetry) {
        return openTelemetry.getTracer("vn.conguyetduong.hogwarts");
    }
}
