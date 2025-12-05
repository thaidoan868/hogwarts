package vn.conguyetduong.hogwarts.app.transfer.dto.profile;

import lombok.Data;

import java.time.Instant;
import java.util.UUID;

@Data
public class AvatarResponse {
    private UUID id;
    private String originalUrl;
    private String mediumUrl;
    private String thumbnailUrl;
    private Boolean isCurrent = true;
    private Instant createdAt;
}
