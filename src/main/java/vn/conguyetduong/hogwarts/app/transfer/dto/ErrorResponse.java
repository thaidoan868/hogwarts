package vn.conguyetduong.hogwarts.app.transfer.dto;

import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.http.HttpStatus;
import vn.conguyetduong.hogwarts.business.exception.ApiException;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class ErrorResponse {
    private String title;
    private String detail;
    private List<String> errors;
    private HttpStatus status;
    private String path;
    private LocalDateTime timestamp;

    public ErrorResponse(String title, String detail, List<String> errors, HttpStatus httpStatus, String path) {
        this.title = title;
        this.detail = detail;
        this.errors = errors;
        this.status = httpStatus;
        this.path = path;
        this.timestamp = LocalDateTime.now();
    }
}
