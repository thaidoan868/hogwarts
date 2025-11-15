package vn.conguyetduong.hogwarts.business.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@RequiredArgsConstructor
@Getter
public enum ErrorCode {
    // generic errors
    _400(HttpStatus.BAD_REQUEST, "Bad request"),
    _401(HttpStatus.UNAUTHORIZED, "Unauthorized"),
    _403(HttpStatus.FORBIDDEN, "Forbidden"),
    _404(HttpStatus.NOT_FOUND, "Resource not found"),
    _409(HttpStatus.CONFLICT, "Resource conflicted"),
    _422(HttpStatus.UNPROCESSABLE_ENTITY, "Validation failed"),

    _500(HttpStatus.INTERNAL_SERVER_ERROR, "Internal server error"),

    // business specific errors
    USER_404(HttpStatus.NOT_FOUND, "User not found"),
    EMAIL_USERNAME_409(HttpStatus.UNPROCESSABLE_ENTITY, "Email or username already exists");

    private final HttpStatus httpStatus;
    private final String title;
}
