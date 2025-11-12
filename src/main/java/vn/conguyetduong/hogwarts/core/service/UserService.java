package vn.conguyetduong.hogwarts.core.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import vn.conguyetduong.hogwarts.infra.adapter.KeycloakAdminClient;
import vn.conguyetduong.hogwarts.infra.model.User;

@Service
@RequiredArgsConstructor
public class UserService {
    private final KeycloakAdminClient keycloakAdapter;

    public User register(User user) {
        return keycloakAdapter.createUser(user);
    }
}

