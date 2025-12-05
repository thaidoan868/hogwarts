package vn.conguyetduong.hogwarts.app.api.v1;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import vn.conguyetduong.hogwarts.app.transfer.dto.profile.AvatarResponse;
import vn.conguyetduong.hogwarts.app.transfer.mapper.AvatarMapper;
import vn.conguyetduong.hogwarts.business.service.AvatarService;
import vn.conguyetduong.hogwarts.business.util.UserUtil;
import vn.conguyetduong.hogwarts.infra.model.profile.Avatar;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/avatars")
@RequiredArgsConstructor
public class AvatarController {
    private final AvatarService service;
    private final AvatarMapper mapper;
    private final Logger log = LoggerFactory.getLogger(AvatarController.class);

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<AvatarResponse> updateAvatar(@RequestParam MultipartFile avatar) {
        Avatar savedAvatar = service.create(avatar);
        return ResponseEntity.ok(mapper.toAvatarResponse(savedAvatar));
    }

    @GetMapping
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<AvatarResponse> getCurrentAvatar() {
        log.trace("GET /api/v1/avatars");
        Avatar avatar = service.getCurrentAvatar(UserUtil.getCurrentUserId());
        return ResponseEntity.ok(mapper.toAvatarResponse(avatar));
    }

    @GetMapping("/list")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<List<AvatarResponse>> getCurrentAvatarList() {
        List<Avatar> avatars = service.getAvatars(UserUtil.getCurrentUserId());
        return ResponseEntity.ok(mapper.toAvatarResponseList(avatars));
    }

    @GetMapping("/{id}")
    public ResponseEntity<AvatarResponse> getCurrentAvatar(@PathVariable("id") UUID userId) {
        log.trace("GET /api/v1/avatars/{userId}");
        Avatar avatar = service.getCurrentAvatar(userId);
        return ResponseEntity.ok(mapper.toAvatarResponse(avatar));
    }
}
