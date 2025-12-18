package vn.conguyetduong.hogwarts.infra.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import vn.conguyetduong.hogwarts.infra.constant.WizardStatus;
import vn.conguyetduong.hogwarts.infra.model.Wizard;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface WizardRepository extends JpaRepository<Wizard, UUID>, JpaSpecificationExecutor<Wizard> {
    Optional<Wizard> findById(UUID id);

    List<Wizard> findAll();

    Page<Wizard> findByStatus(WizardStatus status, Pageable pageable);
}
