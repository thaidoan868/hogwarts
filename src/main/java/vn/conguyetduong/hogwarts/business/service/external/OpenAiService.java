package vn.conguyetduong.hogwarts.business.service.external;

import io.opentelemetry.api.trace.Span;
import io.opentelemetry.api.trace.Tracer;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
import vn.conguyetduong.hogwarts.infra.client.openai.ChatRequest;
import vn.conguyetduong.hogwarts.infra.client.openai.Message;
import vn.conguyetduong.hogwarts.infra.client.openai.OpenAiClient;

import java.util.List;

@Service
@RequiredArgsConstructor
public class OpenAiService {
    private final Tracer tracer;
    private final OpenAiClient openAiClient;

    @Value("${openai.model}")
    private String model;

    public Mono<String> ask(String prompt) {
        return Mono.defer(() -> {
            Span span = tracer.spanBuilder("openai.chat.completion")
                    .setAttribute("openai.model", model)
                    .setAttribute("openai.prompt.length", prompt.length())
                    .startSpan();

            ChatRequest request = new ChatRequest(
                    model,
                    List.of(
                            new Message("system", "Harry potter assistant."),
                            new Message("user", prompt)
                    )
            );

            return openAiClient
                    .post("/chat/completions", request)
                    .doOnNext(response -> {
                        if (response.usage() != null) {
                            span.setAttribute(
                                    "openai.total_tokens",
                                    response.usage().total_tokens()
                            );
                        }
                    })
                    .map(r -> r.choices().getFirst().message().content())
                    .doOnError(span::recordException)
                    .doFinally(signal -> span.end());
        });
    }
}

