package vn.conguyetduong.hogwarts.infra.adapter;


import jakarta.ws.rs.core.Response;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.resource.*;
import org.keycloak.representations.idm.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import vn.conguyetduong.hogwarts.infra.model.User;

import java.util.List;

@Component
public class KeycloakAdminClient {

    private final Keycloak keycloak;
    private final String realm;
    private final boolean defaultVerifyEmail;

    public KeycloakAdminClient(Keycloak keycloak,
                               @Value("${keycloak.realm}") String realm,
                               @Value("${keycloak.verify-email-on-register:true}") boolean defaultVerifyEmail) {
        this.keycloak = keycloak;
        this.realm = realm;
        this.defaultVerifyEmail = defaultVerifyEmail;
    }

    public User createUser(User registerUser) {
        // create user
        UsersResource users = keycloak.realm(realm).users();

        UserRepresentation userRep = new UserRepresentation();
        userRep.setUsername(registerUser.getUsername());
        userRep.setEmail(registerUser.getEmail().toLowerCase());
        userRep.setFirstName(registerUser.getFirstName());
        userRep.setLastName(registerUser.getLastName());
        userRep.setEnabled(true);
        userRep.setEmailVerified(false);

        Response userResponse = users.create(userRep);
        if (userResponse.getStatus() == 409) throw new IllegalArgumentException("Username or email already exists");
        if (userResponse.getStatus() != 201) throw new RuntimeException("Keycloak create failed: HTTP " + userResponse.getStatus());

        // set password
        String userId = userResponse.getLocation().getPath().replaceAll(".*/", "");

        if (!registerUser.getPassword().isBlank()) {
            CredentialRepresentation cred = new CredentialRepresentation();
            cred.setType(CredentialRepresentation.PASSWORD);
            cred.setTemporary(false);
            cred.setValue(registerUser.getPassword());
            users.get(userId).resetPassword(cred);
        }

//        // verify email action
//        if (registerUser.isVerifyEmail() || defaultVerifyEmail) {
//            users.get(userId).executeActionsEmail(List.of("VERIFY_EMAIL"));
//        }

        return User.builder().id(userId).build();
    }
}
