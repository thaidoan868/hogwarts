package vn.conguyetduong.hogwarts.infra.client.openai;

import java.util.List;

public record ChatResponse(List<Choice> choices, Usage usage) {
}
