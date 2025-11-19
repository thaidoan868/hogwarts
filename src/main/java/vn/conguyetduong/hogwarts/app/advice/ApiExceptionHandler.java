package vn.conguyetduong.hogwarts.app.advice;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import vn.conguyetduong.hogwarts.app.transfer.dto.ErrorResponse;
import vn.conguyetduong.hogwarts.business.exception.ApiException;
import vn.conguyetduong.hogwarts.business.exception.ErrorCode;

import java.util.List;

@RestControllerAdvice
public class ApiExceptionHandler {

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
        ErrorCode errorCode = ErrorCode.VALIDATION_FAILED;

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


}
