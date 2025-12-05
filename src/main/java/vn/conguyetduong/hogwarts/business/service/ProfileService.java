package vn.conguyetduong.hogwarts.business.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vn.conguyetduong.hogwarts.business.exception.ApiException;
import vn.conguyetduong.hogwarts.business.exception.ErrorCode;
import vn.conguyetduong.hogwarts.infra.constant.Accessibility;
import vn.conguyetduong.hogwarts.infra.model.profile.Profile;
import vn.conguyetduong.hogwarts.infra.repository.ProfileRepository;

import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ProfileService {
    private final ProfileRepository repo;

    public Profile get(UUID userId) {
        return repo.findByKeycloakUserId(userId).orElseThrow(() ->
                new ApiException(
                        ErrorCode.NOT_FOUND,
                        "Profile with userid " + userId + " not found!"
                )
        );
    }

    @Transactional
    public Profile update(Profile profile) {
        return repo.save(profile);
    }
}
