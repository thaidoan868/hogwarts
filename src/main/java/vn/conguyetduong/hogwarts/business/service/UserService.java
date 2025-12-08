package vn.conguyetduong.hogwarts.business.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vn.conguyetduong.hogwarts.business.content.EmailTemplates;
import vn.conguyetduong.hogwarts.business.exception.ApiException;
import vn.conguyetduong.hogwarts.business.exception.ErrorCode;
import vn.conguyetduong.hogwarts.business.service.external.KeycloakService;
import vn.conguyetduong.hogwarts.business.service.external.MailService;
import vn.conguyetduong.hogwarts.business.util.Generator;
import vn.conguyetduong.hogwarts.infra.model.Token;
import vn.conguyetduong.hogwarts.infra.model.profile.*;
import vn.conguyetduong.hogwarts.infra.model.User;
import vn.conguyetduong.hogwarts.infra.repository.ProfileRepository;
import vn.conguyetduong.hogwarts.infra.repository.TokenRepository;

import java.security.SecureRandom;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.UUID;
import java.util.logging.Logger;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserService {
    private final KeycloakService keycloakService;
    private final ProfileRepository profileRepo;
    private final TokenRepository tokenRepo;
    private final MailService mailService;
    private final TokenService tokenService;


    @Transactional
    public User register(User user) {
        User createdUser = keycloakService.createUser(user);
        log.info("Created User: " + createdUser.getId());

        // create profile
        Profile profile = new Profile(createdUser.getId());
        profile.setAddress(new Address(profile));
        profile.setGender(new Gender(profile));
        profile.setBirthDate(new BirthDate(profile));
        profile.setPhoneNumber(new PhoneNumber(profile));

        // save and return
        profileRepo.save(profile);
        log.info("Created profile with id " + createdUser.getId());
        return createdUser;
    }

    @Transactional
    public void requestResetCode(String username, String emailAddress) {
        // check if the email exists
        User user = keycloakService.getUser(username, emailAddress);

        // if the email has an unexpired token
        if (tokenService.hasUnexpiredToken(emailAddress)) {
            throw new ApiException(
                    ErrorCode.CONFLICT,
                    "Has an unexpired code. Please wait until the code expires"
            );
        }

        // create a token
        Token token = Token.builder()
                .email(emailAddress)
                .code(Generator.code(6))
                .expiresAt(Instant.now().plus(2, ChronoUnit.MINUTES))
                .userId(user.getId())
                .build();
        tokenRepo.save(token);

        // send the reset code to the user's email
        String emailBody = EmailTemplates.resetPassword(user.getFullName(), token.getCode());
        mailService.sendMail(token.getEmail(), EmailTemplates.RESET_PASSWORD_SUBJECT, emailBody);
    }

    public void confirmResetPassword(String email, String code, String newPassword) {
        // check if the code is valid
        Token token = tokenService.get(email, code);
        String userId = token.getUserId().toString();

        // reset password
        keycloakService.resetPassword(userId, newPassword);

        // send a notification
        User user = keycloakService.getUser(userId);
        String emailBody = EmailTemplates.passwordChanged(user.getFullName());
        mailService.sendMail(token.getEmail(), EmailTemplates.PASSWORD_CHANGED_SUBJECT, emailBody);
    }
}

