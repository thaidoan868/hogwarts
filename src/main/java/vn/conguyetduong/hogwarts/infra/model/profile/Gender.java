package vn.conguyetduong.hogwarts.infra.model.profile;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.hibernate.annotations.UuidGenerator;
import vn.conguyetduong.hogwarts.infra.constant.Accessibility;
import vn.conguyetduong.hogwarts.infra.constant.GenderType;

import java.util.UUID;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Gender {
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

    @Enumerated(EnumType.STRING)
    private GenderType gender;

    public Gender(Profile profile) {this.profile = profile;}

}
