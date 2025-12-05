package vn.conguyetduong.hogwarts.infra.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import vn.conguyetduong.hogwarts.infra.model.profile.Profile;

import java.util.Optional;
import java.util.UUID;

public interface ProfileRepository extends JpaRepository<Profile, UUID> {
    Optional<Profile> findByKeycloakUserId(UUID keycloakUserId);
}