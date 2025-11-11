package vn.conguyetduong.hogwarts.infra.model;

import lombok.Builder;
import lombok.Data;
import java.util.Objects;

@Data
@Builder
public class User {
    private final String id;
    private final String username;
    private final String email;
    private final String firstName;
    private final String lastName;
    private final String password;
    private final boolean emailVerified;

    public String fullName() { return (firstName + " " + lastName).trim(); }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof User)) return false;
        User u = (User) o;
        return Objects.equals(id, u.id);
    }
}