package vn.conguyetduong.hogwarts.business.service.external;


import jakarta.validation.Validator;
import jakarta.ws.rs.NotFoundException;
import jakarta.ws.rs.core.Response;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.keycloak.admin.client.CreatedResponseUtil;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.resource.*;
import org.keycloak.representations.idm.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.stereotype.Service;
import vn.conguyetduong.hogwarts.business.exception.ApiException;
import vn.conguyetduong.hogwarts.business.exception.ErrorCode;
import vn.conguyetduong.hogwarts.business.util.ValidateUtil;
import vn.conguyetduong.hogwarts.infra.model.User;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class KeycloakService {
    private final Keycloak keycloak;
    @Value("${keycloak.realm}")
    private String realm;
    private final Validator validator;

    private UserRepresentation toUserRepresentation(User user) {
        ValidateUtil.annotations(validator, user);

        UserRepresentation userRep = new UserRepresentation();
        userRep.setUsername(user.getUsername());
        userRep.setEmail(user.getEmail().toLowerCase());
        userRep.setFirstName(user.getFirstName());
        userRep.setLastName(user.getLastName());
        userRep.setEnabled(true);
        userRep.setEmailVerified(false);
        userRep.setRequiredActions(List.of("VERIFY_EMAIL"));
        return userRep;
    }

    private User toDomainUser(UserRepresentation rep) {
        if (rep == null)  return null;
        return User.builder()
                .id(UUID.fromString(rep.getId()))
                .username(rep.getUsername())
                .email(rep.getEmail())
                .firstName(rep.getFirstName())
                .lastName(rep.getLastName())
                .build();
    }

    public User getUser(String id) {
        UsersResource users = keycloak.realm(realm).users();
        UserResource userResource = users.get(id);

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
        return toDomainUser(rep);
    }

    public User getUser(String username, String email) {
        UsersResource usersResource = keycloak.realm(realm).users();

        List<UserRepresentation> candidates = usersResource.search(username, null, null, email, 0, 1);
        UserRepresentation userRep = candidates.stream()
                .filter(u -> username.equalsIgnoreCase(u.getUsername()))
                .filter(u -> email.equalsIgnoreCase(u.getEmail()))
                .findFirst()
                .orElseThrow(() ->
                        new ApiException(
                                ErrorCode.NOT_FOUND,
                                "User with username '%s' and email '%s' not found".formatted(username, email)
                        )
                );
        return toDomainUser(userRep);
    }

    public User createUser(User registerUser) {
        UsersResource usersResource = keycloak.realm(realm).users();

        // creation
        Response userResponse = usersResource.create(toUserRepresentation(registerUser));
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

        // Get the created user
        String userId = CreatedResponseUtil.getCreatedId(userResponse);
        UserResource createdUserResource = usersResource.get(userId);
        log.info("Created Keycloak user with id={}", userId);

        // set password
        resetPassword(userId, registerUser.getPassword());

        // send verify email
        createdUserResource.sendVerifyEmail();

        return new User(UUID.fromString(userId));
    }

    public void resetPassword(String userId, String newPassword) {
        UsersResource usersResource = keycloak.realm(realm).users();
        UserResource userResource = usersResource.get(userId);

        CredentialRepresentation passwordCred = new CredentialRepresentation();
        passwordCred.setType(CredentialRepresentation.PASSWORD);
        passwordCred.setValue(newPassword);
        passwordCred.setTemporary(false);

        userResource.resetPassword(passwordCred);

        userResource.logout();

        log.info("Password reset and sessions invalidated for userId={}", userId);
    }
}
