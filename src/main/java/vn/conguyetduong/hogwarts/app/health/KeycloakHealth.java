package vn.conguyetduong.hogwarts.app.health;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.resource.UsersResource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class KeycloakHealth implements HealthIndicator {

    private final Keycloak keycloak;

    @Value("${keycloak.realm}")
    private String realm;

    @Override
    public Health health() {
        try {
            UsersResource usersResource = keycloak.realm(realm).users();

            int count = usersResource.count();

            return Health.up()
                    .withDetail("realm", realm)
                    .withDetail("usersCount", count)
                    .build();

        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return Health.down(e)
                    .withDetail("realm", realm)
                    .build();
        }
    }
}
