package vn.conguyetduong.hogwarts.business.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vn.conguyetduong.hogwarts.business.service.external.KeycloakService;
import vn.conguyetduong.hogwarts.infra.model.profile.*;
import vn.conguyetduong.hogwarts.infra.model.User;
import vn.conguyetduong.hogwarts.infra.repository.ProfileRepository;

import java.util.UUID;
import java.util.logging.Logger;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserService {
    private final KeycloakService keycloakService;
    private final ProfileRepository profileRepo;
    private final Logger log =  Logger.getLogger(this.getClass().getName());

    @Transactional
    public User register(User user) {
        User createdUser = keycloakService.createUser(user);
        log.info("Created User: " + createdUser.getId());

        // create profile
        Profile profile = new Profile(createdUser.getId());

        profile.setAddress(new Address(profile));
        profile.setGender(new Gender(profile));
        profile.setBirthDate(new BirthDate(profile));
        profile.setPhoneNumber(new PhoneNumber(profile));

        // save and return
        profileRepo.save(profile);
        log.info("Created profile with id " + createdUser.getId());
        return createdUser;
    }
}

