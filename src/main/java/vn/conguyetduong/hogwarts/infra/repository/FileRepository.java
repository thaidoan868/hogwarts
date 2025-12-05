package vn.conguyetduong.hogwarts.infra.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import vn.conguyetduong.hogwarts.infra.model.File;

import java.util.UUID;

public interface FileRepository extends JpaRepository<File, UUID> {
}

