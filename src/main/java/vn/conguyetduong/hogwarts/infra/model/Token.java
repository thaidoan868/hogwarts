package vn.conguyetduong.hogwarts.infra.model;

import jakarta.persistence.Entity;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.*;

import java.time.Instant;
import java.util.UUID;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Token {
    @Id
    @UuidGenerator
    private UUID id;

    @NotNull
    private UUID userId;

    @NotBlank
    private String email;

    @NotNull
    private String code;

    @NotNull
    private Instant expiresAt;
}
