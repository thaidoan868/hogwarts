package vn.conguyetduong.hogwarts.infra.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import vn.conguyetduong.hogwarts.infra.model.Token;

import java.time.Instant;
import java.util.Optional;

public interface TokenRepository extends JpaRepository<Token, Long> {
    Optional<Token> findFirstByEmailAndCodeAndExpiresAtAfter(String email, String code, Instant now);
    boolean existsByEmailAndExpiresAtAfter(String email, Instant now);
}
