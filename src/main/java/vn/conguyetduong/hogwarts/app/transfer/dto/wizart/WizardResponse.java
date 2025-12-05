package vn.conguyetduong.hogwarts.app.transfer.dto.wizart;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

@Data
@AllArgsConstructor
@Builder
public class WizardResponse {
    private UUID id;
    private String name;
    private String description;
    private Long viewCount;
    private Instant createdAt;
    private String createdBy;
    private Instant updatedAt;
    private String updatedBy;
    private List<ImageResponse> images;
}