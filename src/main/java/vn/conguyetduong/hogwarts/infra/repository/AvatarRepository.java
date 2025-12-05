package vn.conguyetduong.hogwarts.infra.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import vn.conguyetduong.hogwarts.infra.model.profile.Avatar;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface AvatarRepository extends JpaRepository<Avatar, UUID> {
    @Modifying
    @Query("UPDATE Avatar a SET a.isCurrent = false WHERE a.userId = :userId")
    void unsetCurrentAvatars(@Param("userId") UUID userId);

    Optional<Avatar> findFirstByUserIdAndIsCurrentTrue(UUID userId);

    List<Avatar> findByUserId(UUID userId);
}
