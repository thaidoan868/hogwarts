package vn.conguyetduong.hogwarts.app.advice;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import vn.conguyetduong.hogwarts.app.transfer.dto.ErrorResponse;
import vn.conguyetduong.hogwarts.business.exception.ErrorCode;

@RestControllerAdvice
public class FileExceptionHandler {
    @Value("${spring.servlet.multipart.max-file-size}")
    private String maxFileSize;
    @Value("${spring.servlet.multipart.max-request-size}")
    private String maxRequestSize;

    @ExceptionHandler(MaxUploadSizeExceededException.class)
    public ResponseEntity<ErrorResponse> handleMaxUploadSizeExceeded(MaxUploadSizeExceededException e, HttpServletRequest request) {
        ErrorCode errorCode = ErrorCode.EXCEED_MAXSIZE;
        String detail = "The file exceeded the maximum upload size. Max file size: %s. Max request size: %s".formatted(maxFileSize, maxRequestSize);
        ErrorResponse body = new ErrorResponse(
                errorCode.getTitle(),
                detail,
                null,
                errorCode.getHttpStatus(),
                request.getRequestURI()
        );

        return ResponseEntity.status(errorCode.getHttpStatus()).body(body);
    }
}
