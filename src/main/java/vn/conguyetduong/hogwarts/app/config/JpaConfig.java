package vn.conguyetduong.hogwarts.app.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.auditing.DateTimeProvider;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.stereotype.Component;
import vn.conguyetduong.hogwarts.business.util.UserUtil;

import java.time.OffsetDateTime;
import java.util.Optional;
import java.util.UUID;


@Configuration
@EnableJpaAuditing
public class JpaConfig {

    @Bean
    public AuditorAware<UUID> auditorAware() {
        return () -> Optional.of(UserUtil.getCurrentUserId());
    }
}