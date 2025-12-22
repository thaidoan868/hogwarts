package vn.conguyetduong.hogwarts.infra.client.openai;

public record Error(
        String message,
        String type,
        String code
) {}
