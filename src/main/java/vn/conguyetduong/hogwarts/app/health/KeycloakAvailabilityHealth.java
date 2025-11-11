package vn.conguyetduong.hogwarts.app.health;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

@Component
public class KeycloakAvailabilityHealth implements HealthIndicator {
    private final WebClient webClient = WebClient.builder().build();

    @Value("${keycloak.server-url}")
    String serverUrl;

    @Value("${keycloak.realm}")
    String realm;

    @Override
    public Health health() {
        try {
            webClient.get()
                    .uri(serverUrl + "/realms/" + realm + "/.well-known/openid-configuration")
                    .retrieve()
                    .toBodilessEntity()
                    .block();
            return Health.up().withDetail("endpoint", "openid-configuration").build();
        } catch (Exception e) {
            return Health.down(e).build();
        }
    }
}