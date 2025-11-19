package vn.conguyetduong.hogwarts.infra.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import vn.conguyetduong.hogwarts.infra.model.Image;

import java.util.UUID;

public interface ImageRepository extends JpaRepository<Image, UUID> {
}

