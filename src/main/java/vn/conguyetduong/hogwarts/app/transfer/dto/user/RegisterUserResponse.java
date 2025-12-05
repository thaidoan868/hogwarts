package vn.conguyetduong.hogwarts.app.transfer.dto.user;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class RegisterUserResponse {
    private String userId;
}
