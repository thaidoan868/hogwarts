package vn.conguyetduong.hogwarts.infra.model.profile;

import jakarta.persistence.Entity;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.hibernate.annotations.UuidGenerator;
import vn.conguyetduong.hogwarts.infra.constant.Accessibility;

import java.util.UUID;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Address {
    @Id
    @UuidGenerator
    private UUID id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "profile_id")
    @NotNull
    @ToString.Exclude
    private Profile profile;

    @NotNull
    @Enumerated(EnumType.STRING)
    private Accessibility accessibility = Accessibility.PUBLIC;

    private String address;

    public Address(Profile profile) {this.profile = profile;}
}
