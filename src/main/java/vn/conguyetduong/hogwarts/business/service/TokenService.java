package vn.conguyetduong.hogwarts.business.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vn.conguyetduong.hogwarts.business.exception.ApiException;
import vn.conguyetduong.hogwarts.business.exception.ErrorCode;
import vn.conguyetduong.hogwarts.infra.model.Token;
import vn.conguyetduong.hogwarts.infra.repository.TokenRepository;

import java.time.Instant;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class TokenService {
    private final TokenRepository repo;

    @Transactional
    public Token create(Token token) {
        return repo.save(token);
    }

    public Token get(String email, String code) {
        return repo.findFirstByEmailAndCodeAndExpiresAtAfter(email, code, Instant.now()).orElseThrow(() ->
                new ApiException(
                        ErrorCode.NOT_FOUND,
                        "Token with email '%s' and code '%s' not found'".formatted(email, code)
                )
        );
    }

    public boolean hasUnexpiredToken(String email) {
        return repo.existsByEmailAndExpiresAtAfter(email, Instant.now());
    }
}

