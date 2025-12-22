package vn.conguyetduong.hogwarts.business.exception;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.http.HttpStatus;

import java.util.List;

@Data
@AllArgsConstructor
public class ApiException extends RuntimeException {
    private final ErrorCode errorCode;
    private final String detail;
    private final List<String> errors;

    public HttpStatus getHttpStatus() {
        return errorCode.getHttpStatus();
    }

    public String getTitle() {
        return errorCode.getTitle();
    }

    public ApiException(ErrorCode errorCode, String detail) {
        super(detail);
        this.errorCode = errorCode;
        this.detail = detail;
        this.errors = null;
    }

}

