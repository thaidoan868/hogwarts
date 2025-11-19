package vn.conguyetduong.hogwarts.business.util;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import vn.conguyetduong.hogwarts.infra.model.User;

import java.util.UUID;

public class Utility {
    private static final Authentication auth = SecurityContextHolder.getContext().getAuthentication();
    private static  final Jwt jwt = (Jwt) auth.getPrincipal();

    public User getCurrentUser(){
        return User.builder()
                .username(auth.getName())
                .id(UUID.fromString(jwt.getSubject()))
                .firstName(jwt.getClaimAsString("given_name"))
                .lastName(jwt.getClaimAsString("family_name"))
                .email(jwt.getClaimAsString("email"))
                .build();
    }

    public static UUID getCurrentUserId(){
        return UUID.fromString(jwt.getSubject());
    }

    public static boolean isUsernameOrEmailError(String message) {
        if (message == null) return false;
        String m = message.toLowerCase();
        return m.contains("username") || m.contains("email");
    }
}
