package vn.conguyetduong.hogwarts.app.api.v1;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import vn.conguyetduong.hogwarts.app.transfer.dto.profile.*;
import vn.conguyetduong.hogwarts.app.transfer.mapper.AvatarMapper;
import vn.conguyetduong.hogwarts.app.transfer.mapper.ProfileMapper;
import vn.conguyetduong.hogwarts.business.service.AvatarService;
import vn.conguyetduong.hogwarts.business.service.ProfileService;
import vn.conguyetduong.hogwarts.business.util.UserUtil;
import vn.conguyetduong.hogwarts.infra.model.User;
import vn.conguyetduong.hogwarts.infra.model.profile.Avatar;
import vn.conguyetduong.hogwarts.infra.model.profile.Profile;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/profiles")
@RequiredArgsConstructor
public class ProfileController {
    private final ProfileService service;
    private final ProfileMapper mapper;
    private final AvatarMapper avatarMapper;
    private final AvatarService avatarService;
    private final Logger log = LoggerFactory.getLogger(ProfileController.class);

    @GetMapping
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<DetailedProfileResponse> getProfile() {
        User user = UserUtil.getCurrentUser();
        Profile profile = service.get(user.getId());

        DetailedProfileResponse profileResponse = mapper.toProfileResponse(profile);
        profileResponse.setUsername(user.getUsername());

        return ResponseEntity.ok(profileResponse);
    }

    @PatchMapping
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<?> patchUpdate(@RequestBody ProfileRequest request) {
        // get the profile
        Profile oldProfile = service.get(UserUtil.getCurrentUserId());

        // mapper and update
        Profile updateProfile = mapper.patchUpdate(request, oldProfile);
        service.update(updateProfile);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProfileResponse>  getPublicProfile(
            @PathVariable("id") UUID userID,
            @RequestParam(defaultValue = "false") boolean details
    ) {
        if (details) {
            Profile profile = service.get(userID);
            return ResponseEntity.ok(mapper.toPublicProfileResponse(profile));
        } else  {
            User currentUser = UserUtil.getCurrentUser();
            Avatar avatar = avatarService.getCurrentAvatar(currentUser.getId());
            AvatarResponse avatarResponse = avatarMapper.toAvatarResponse(avatar);
            ShortProfileResponse response = ShortProfileResponse.builder()
                    .userID(currentUser.getId())
                    .username(currentUser.getUsername())
                    .fullName(currentUser.getFullName())
                    .avatar(avatarResponse)
                    .build();
            return ResponseEntity.ok(response);
        }
    }
}


