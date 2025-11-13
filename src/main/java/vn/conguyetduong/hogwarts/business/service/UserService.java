package vn.conguyetduong.hogwarts.business.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vn.conguyetduong.hogwarts.infra.adapter.KeycloakAdminClient;
import vn.conguyetduong.hogwarts.infra.model.Profile;
import vn.conguyetduong.hogwarts.infra.model.User;
import vn.conguyetduong.hogwarts.infra.repository.ProfileRepository;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserService {
    private final KeycloakAdminClient keycloakAdapter;
    private final ProfileRepository profileRepo;

    @Transactional
    public User register(User user) {
        User createdUser = keycloakAdapter.createUser(user);
        Profile profile = new Profile(createdUser.getId());
        profileRepo.save(profile);
        return createdUser;
    }
}

