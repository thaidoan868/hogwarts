package vn.conguyetduong.hogwarts.app.advice;

import jakarta.validation.ConstraintViolationException;
import jakarta.ws.rs.ClientErrorException;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.resource.NoResourceFoundException;
import vn.conguyetduong.hogwarts.app.transfer.dto.ErrorResponse;
import vn.conguyetduong.hogwarts.business.exception.ApiException;
import vn.conguyetduong.hogwarts.business.exception.ErrorCode;

import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import vn.conguyetduong.hogwarts.business.util.Utility;

@RestControllerAdvice
public class GlobalExceptionHandler {
    private static final Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);


    @ExceptionHandler(ApiException.class)
    public ResponseEntity<ErrorResponse> handleApiException(ApiException ex, HttpServletRequest request) {
        ErrorResponse body = new ErrorResponse(
            ex.getTitle(),
            ex.getDetail(),
            ex.getErrors(),
            ex.getHttpStatus(),
            request.getRequestURI()
        );

        return ResponseEntity
                .status(ex.getHttpStatus())
                .body(body);
    }


    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpServletRequest request) {
        List<String> errors = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(fieldError -> fieldError.getField() + ": " + fieldError.getDefaultMessage())
                .toList();

        String detail = "Validation failed with " + errors.size() + " error(s).";
        ErrorCode errorCode = ErrorCode._422;

        ErrorResponse body = new ErrorResponse(
                errorCode.getTitle(),
                detail,
                errors,
                errorCode.getHttpStatus(),
                request.getRequestURI()
        );

        return ResponseEntity
                .status(errorCode.getHttpStatus())
                .body(body);
    }


    // Keycloak exceptions
    @ExceptionHandler({OAuth2AuthenticationException.class})
    public ResponseEntity<ErrorResponse> handleJwtAuthException(Exception ex, HttpServletRequest request) {
        ErrorCode errorCode = ErrorCode._401;
        String detail = ex.getMessage();

        ErrorResponse body = new ErrorResponse(
                errorCode.getTitle(),
                detail,
                null,
                errorCode.getHttpStatus(),
                request.getRequestURI()
        );

        return ResponseEntity
                .status(errorCode.getHttpStatus())
                .body(body);
    }


    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<ErrorResponse> handleAccessDenied(AccessDeniedException ex, HttpServletRequest request) {
        ErrorCode errorCode = ErrorCode._403;

        String detail = ex.getMessage();

        ErrorResponse body = new ErrorResponse(
                errorCode.getTitle(),
                detail,
                null,
                errorCode.getHttpStatus(),
                request.getRequestURI()
        );

        return ResponseEntity
                .status(errorCode.getHttpStatus())
                .body(body);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ErrorResponse> handleUserExists(IllegalArgumentException ex, HttpServletRequest request) {
        if (!Utility.isUsernameOrEmailError(ex.getMessage())) {
            throw ex;
        }

        ErrorCode errorCode = ErrorCode._409;
        ErrorResponse body = new ErrorResponse(
                errorCode.getTitle(),
                ex.getMessage(),
                null,
                errorCode.getHttpStatus(),
                request.getRequestURI()
        );

        return ResponseEntity
                .status(errorCode.getHttpStatus())
                .body(body);
    }

    // Generic exception handler
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ErrorResponse> handleHttpMessageNotReadable(HttpServletRequest request) {
        ErrorCode errorCode = ErrorCode._400;

        String detail = "Request body is invalid or unreadable.";


        ErrorResponse body = new ErrorResponse(
                errorCode.getTitle(),
                detail,
                null,
                errorCode.getHttpStatus(),
                request.getRequestURI()
        );

        return ResponseEntity
                .status(errorCode.getHttpStatus())
                .body(body);
    }

    @ExceptionHandler(NoResourceFoundException.class)
    public ResponseEntity<ErrorResponse> handleNotFound(HttpServletRequest request) {

        ErrorCode errorCode = ErrorCode._404;
        String detail = "Endpoint does not exist";

        ErrorResponse body = new ErrorResponse(
                errorCode.getTitle(),
                detail,
                null,
                errorCode.getHttpStatus(),
                request.getRequestURI()
        );

        return ResponseEntity.status(errorCode.getHttpStatus()).body(body);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleUncaughtException(Exception ex, HttpServletRequest request) {
        log.error("Uncaught exception occurred at path {}", request.getRequestURI(), ex);

        ErrorCode errorCode = ErrorCode._500;
        String detail = "An unexpected error occurred.";

         ErrorResponse body = new ErrorResponse(
            errorCode.getTitle(),
            detail,
            null,
            errorCode.getHttpStatus(),
            request.getRequestURI()
         );
         return ResponseEntity
                 .status(errorCode.getHttpStatus())
                 .body(body);
    }
}
