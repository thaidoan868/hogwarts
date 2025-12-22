package vn.conguyetduong.hogwarts.infra.client.openai;

import java.util.List;

public record ChatRequest(String model, List<Message> messages) {
}
