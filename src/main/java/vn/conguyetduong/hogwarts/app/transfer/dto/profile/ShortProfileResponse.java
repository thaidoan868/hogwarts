package vn.conguyetduong.hogwarts.app.transfer.dto.profile;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.UUID;

@Data
@AllArgsConstructor
@Builder
public class ShortProfileResponse  implements ProfileResponse {
    UUID userID;
    String username;
    String fullName;
    AvatarResponse avatar;
}
