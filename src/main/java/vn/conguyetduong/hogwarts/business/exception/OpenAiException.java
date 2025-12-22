package vn.conguyetduong.hogwarts.business.exception;

import lombok.Data;

@Data
public class OpenAiException extends RuntimeException {
    private final int httpStatus;
    private final String type;
    private final String code;

    public OpenAiException(int httpStatus, String message, String type, String code) {
        super(message);
        this.httpStatus = httpStatus;
        this.type = type;
        this.code = code;
    }
}
