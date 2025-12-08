package vn.conguyetduong.hogwarts.infra.model;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.Objects;
import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
public class User {
    private UUID id;
    @NotBlank
    private String username;
    @NotBlank
    private String email;
    @NotBlank
    private String firstName;
    @NotBlank
    private String lastName;
    @NotBlank
    private String password;
    @Builder.Default
    private boolean isEmailVerified = false;

    public String getFullName() {
        return (firstName + " " + lastName).trim();
    }

    public User(UUID id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof User)) return false;
        User u = (User) o;
        return Objects.equals(id, u.id);
    }
}