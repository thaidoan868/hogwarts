package vn.conguyetduong.hogwarts.app.health;

import lombok.RequiredArgsConstructor;
import org.keycloak.admin.client.resource.UsersResource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class KeycloakHealth implements HealthIndicator {

    private final org.keycloak.admin.client.Keycloak keycloak;

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
            return Health.down(e)
                    .withDetail("realm", realm)
                    .build();
        }
    }
}
