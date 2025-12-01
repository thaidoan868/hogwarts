package vn.conguyetduong.hogwarts.business.service.external;


import jakarta.ws.rs.NotFoundException;
import jakarta.ws.rs.core.Response;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.resource.*;
import org.keycloak.representations.idm.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import vn.conguyetduong.hogwarts.business.exception.ApiException;
import vn.conguyetduong.hogwarts.business.exception.ErrorCode;
import vn.conguyetduong.hogwarts.infra.model.User;

import java.util.UUID;

@Service
public class KeycloakService {

    private final Keycloak keycloak;
    private final String realm;
    private final boolean defaultVerifyEmail;
    private static final Logger log = LoggerFactory.getLogger(KeycloakService.class);


    public KeycloakService(Keycloak keycloak,
                           @Value("${keycloak.realm}") String realm,
                           @Value("${keycloak.verify-email-on-register:true}") boolean defaultVerifyEmail) {
        this.keycloak = keycloak;
        this.realm = realm;
        this.defaultVerifyEmail = defaultVerifyEmail;
    }
    private UserRepresentation toUserRepresentation(User user) {
        UserRepresentation userRep = new UserRepresentation();
        userRep.setUsername(user.getUsername());
        userRep.setEmail(user.getEmail().toLowerCase());
        userRep.setFirstName(user.getFirstName());
        userRep.setLastName(user.getLastName());
        userRep.setEnabled(true);
        userRep.setEmailVerified(false);
        return userRep;
    }

    private User toDomainUser(UUID id, UserRepresentation rep) {
        User user = User.builder()
                .id(id)
                .username(rep.getUsername())
                .email(rep.getEmail())
                .firstName(rep.getFirstName())
                .lastName(rep.getLastName())
                .build();
        return user;
    }

    public User getUser(UUID id) {
        UsersResource users = keycloak.realm(realm).users();
        UserResource userResource = users.get(id.toString());

        UserRepresentation rep;
        try {
            rep = userResource.toRepresentation();
        } catch (NotFoundException e) {
            throw new ApiException(
                    ErrorCode.NOT_FOUND,
                    "User with id '%s' not found".formatted(id)
            );
        } catch (Exception e) {
            log.error("Keycloak failed to get user representation", e);
            throw new ApiException(ErrorCode.INTERNAL_ERROR, null);
        }

        return toDomainUser(id, rep);
    }

    public User createUser(User registerUser) {
        // create user
        UsersResource users = keycloak.realm(realm).users();

        Response userResponse = users.create(toUserRepresentation(registerUser));
        if (userResponse.getStatus() == 409) throw new ApiException(
                ErrorCode.CONFLICT,
                "username: %s or email %s already exists".formatted(registerUser.getUsername(), registerUser.getEmail())
        );
        if (userResponse.getStatus() != 201) {
            String body = userResponse.readEntity(String.class);
            log.error("Keycloak create user failed. Status: {}", userResponse.getStatus());
            log.error("Keycloak error body: {}", body);
            throw new ApiException(
                ErrorCode.INTERNAL_ERROR,
                "Keycloak failed to create new user with email %s".formatted(registerUser.getEmail())
            );
        }

        // set password
        // extract user id from location path. Location path format: /admin/{realm}/users/{id}
        String[] segments = userResponse.getLocation().getPath().split("/");
        String idStr = segments[segments.length - 1];

        UUID userId = UUID.fromString(idStr);

        if (!registerUser.getPassword().isBlank()) {
            CredentialRepresentation cred = new CredentialRepresentation();
            cred.setType(CredentialRepresentation.PASSWORD);
            cred.setTemporary(false);
            cred.setValue(registerUser.getPassword());
            users.get(userId.toString()).resetPassword(cred);
        }

//        // verify email action
//        if (registerUser.isVerifyEmail() || defaultVerifyEmail) {
//            users.get(userId).executeActionsEmail(List.of("VERIFY_EMAIL"));
//        }

        return new User(userId);
    }
}
