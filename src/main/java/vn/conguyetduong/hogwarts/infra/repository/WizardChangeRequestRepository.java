package vn.conguyetduong.hogwarts.infra.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import vn.conguyetduong.hogwarts.infra.model.WizardChangeRequest;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface WizardChangeRequestRepository extends JpaRepository<WizardChangeRequest, UUID> {
    List<WizardChangeRequest> findAll();
    List<WizardChangeRequest> findAllByCreatedBy(UUID createdBy);
    Optional<WizardChangeRequest> findById(UUID id);
}
