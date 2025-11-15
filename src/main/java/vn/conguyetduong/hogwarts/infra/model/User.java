package vn.conguyetduong.hogwarts.infra.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;

import java.util.Objects;
import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
public class User {
    private UUID id;
    private String username;
    private String email;
    private String firstName;
    private String lastName;
    private String password;
    private boolean isEmailVerified;

    public String fullName() {
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