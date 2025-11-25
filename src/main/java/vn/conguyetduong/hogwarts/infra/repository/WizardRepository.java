package vn.conguyetduong.hogwarts.infra.repository;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import vn.conguyetduong.hogwarts.infra.model.Wizard;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface WizardRepository extends JpaRepository<Wizard, UUID> {
    @EntityGraph(attributePaths = "images")
    Optional<Wizard> findById(UUID id);

    @EntityGraph(attributePaths = "images")
    List<Wizard> findAll();
}
